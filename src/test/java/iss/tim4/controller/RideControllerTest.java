package iss.tim4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import iss.tim4.domain.RideStatus;
import iss.tim4.domain.model.Role;
import iss.tim4.security.jwt.JwtTokenUtil;
import iss.tim4.service.*;
import org.hamcrest.Matchers;
import org.jboss.jandex.JandexAntTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RideControllerTest {

    private static final Integer VALID_RIDE_ID = 333;
    private static final Integer INVALID_RIDE_ID = 999;
    private static final Integer ACCEPTED_RIDE_ID = 1234;
    private static final Integer ACTIVE_RIDE_ID = 1235;

    private static final Integer PASSENGER_ID = 111;
    private static final Integer SECOND_PASSENGER_ID = 400;
    private static final Integer INVALID_PASSENGER_ID = 543;

    private static final Integer INVALID_DRIVER_ID = 666;

    private static final Integer DRIVER_ID = 222;
    private static final Integer SECOND_DRIVER_ID = 987;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static String DRIVER_TOKEN;
    private static String PASSENGER_TOKEN;
    private static String ADMIN_TOKEN;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    @Sql("classpath:data-test.sql")
    public void setUp(){
        DRIVER_TOKEN = jwtTokenUtil.generateToken("driver-test@gmail.com", Role.DRIVER, 222);
        PASSENGER_TOKEN = jwtTokenUtil.generateToken("passenger-test@gmail.com", Role.PASSENGER, 111);
        // ADMIN -> TO DO KREIRAJ I ADMINA :)

    }

    @Test
    @DisplayName("Test Should Retrieve Ride By Id When Making GET Request To Endpoint - /api/ride/{id}")
    @Sql("classpath:data-test.sql")
    public void shouldGetRideById() throws Exception {
        mockMvc.perform(get("/api/ride/" + VALID_RIDE_ID))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(VALID_RIDE_ID)))
                .andExpect(jsonPath("$.totalCost", Matchers.is(500.0)))
                .andExpect(jsonPath("$.estimatedTimeInMinutes", Matchers.is(102.2)))
                .andExpect(jsonPath("$.kilometers", Matchers.is(0.9)))
                .andExpect(jsonPath("$.petTransport", Matchers.is(false)))
                .andExpect(jsonPath("$.babyTransport", Matchers.is(true)))
                .andExpect(jsonPath("$.driver.id", Matchers.is(DRIVER_ID)))
                .andExpect(jsonPath("$.passengers[0].id", Matchers.is(PASSENGER_ID)));

    }


    @Test
    @DisplayName("Test Should Have Status Not Found For Getting Ride With Invalid ID - /api/ride/{id}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnRideNotFound() throws Exception {
        mockMvc.perform(get("/api/ride/" + INVALID_RIDE_ID))
                .andExpect(status().is(404))
                .andExpect(content().string("Ride does not exist"));

    }

    /* TODO IMPLEMENTIRATI KADA ZAVRSIMO DO KRAJA POST RIDE :)
    @Test
    @DisplayName("Test Should Post New Ride To Endpoint - /api/ride")
    @Sql("classpath:data-test.sql")
    public void shouldPostRide() throws Exception{
        RouteDTO routeDTO = new RouteDTO(new LocationDTO("Svetislava Kasapinovica 33", 45.1, 45.2),
                                        new LocationDTO("Janka Cmelika 12", 45.3, 45.4));
        PassengerDTOResult passengerDTO = new PassengerDTOResult(111,"Alex",
                                                        "Mishutkin","alex.jpg", "+7 977 1123",
                                                         "passenger-test@gmail.com", "NS, 21000" ,
                                                        true, false);
        RideDTORequest rideDTO = new RideDTORequest(VehicleName.LUXURY, true,
                true, new PassengerDTOResult[]{passengerDTO}, new RouteDTO[]{routeDTO},LocalDateTime.now(),
                4.0, 4.0);

        String token = jwtTokenUtil.generateToken("driver-test@gmail.com", Role.DRIVER, 222);
        this.objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(post("/api/ride")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(rideDTO))
                .header("Authorization","Bearer " + token))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.kilometers", Matchers.is(4.0)));
    }

    @Test
    @DisplayName("Test Should Not Find Driver For Van /api/ride")
    @Sql("classpath:data-test.sql")
    public void shouldNotFindDriverForVan() throws Exception{
        RouteDTO routeDTO = new RouteDTO(new LocationDTO("Svetislava Kasapinovica 33", 45.1, 45.2),
                new LocationDTO("Janka Cmelika 12", 45.3, 45.4));
        PassengerDTOResult passengerDTO = new PassengerDTOResult(111,"Alex",
                "Mishutkin","alex.jpg", "+7 977 1123",
                "passenger-test@gmail.com", "NS, 21000" ,
                true, false);
        RideDTORequest rideDTO = new RideDTORequest(VehicleName.VAN, true,
                true, new PassengerDTOResult[]{passengerDTO}, new RouteDTO[]{routeDTO},LocalDateTime.now(),
                4.0, 4.0);

        String token = jwtTokenUtil.generateToken("driver-test@gmail.com", Role.DRIVER, 222);
        this.objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(post("/api/ride")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(rideDTO))
                        .header("Authorization","Bearer " + token))
                .andExpect(status().is(404));
    }



*/
    @Test
    @DisplayName("Test Should Retrieve Passenger Ride History /api/ride/passenger/rideHistory")
    @Sql("classpath:data-test.sql")
    public void shouldFindPassengerRideHistory() throws Exception{
        mockMvc.perform(get("/api/ride/passenger/" + PASSENGER_ID + "/rideHistory"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(VALID_RIDE_ID)))
                .andExpect(jsonPath("$[0].totalCost", Matchers.is(500.0)))
                .andExpect(jsonPath("$[0].estimatedTimeInMinutes", Matchers.is(102.2)))
                .andExpect(jsonPath("$[0].kilometers", Matchers.is(0.9)))
                .andExpect(jsonPath("$[0].petTransport", Matchers.is(false)))
                .andExpect(jsonPath("$[0].babyTransport", Matchers.is(true)));
    }

    @Test
    @DisplayName("Test Should Return Not Found Status For Invalid Passenger ID /api/ride/passenger/rideHistory")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalidPassenger() throws Exception{
        mockMvc.perform(get("/api/ride/passenger/" + INVALID_PASSENGER_ID + "/rideHistory"))
                .andExpect(status().is(404));

    }

    @Test
    @DisplayName("Test Should Retrieve Driver Ride History /api/ride/driver/rideHistory")
    @Sql("classpath:data-test.sql")
    public void shouldFindDriverRideHistory() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + DRIVER_ID + "/rideHistory"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(VALID_RIDE_ID)))
                .andExpect(jsonPath("$[0].totalCost", Matchers.is(500.0)))
                .andExpect(jsonPath("$[0].estimatedTimeInMinutes", Matchers.is(102.2)))
                .andExpect(jsonPath("$[0].kilometers", Matchers.is(0.9)))
                .andExpect(jsonPath("$[0].petTransport", Matchers.is(false)))
                .andExpect(jsonPath("$[0].babyTransport", Matchers.is(true)))
                .andExpect(jsonPath("$[0].driver.id", Matchers.is(DRIVER_ID)));
    }

    @Test
    @DisplayName("Test Should Return Not Found Status For Invalid Driver ID /api/ride/driver/rideHistory")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalidDriver() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + INVALID_DRIVER_ID + "/rideHistory"))
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("Test Should Return Driver Rides Between Two Dates  /api/ride/driver/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnDriverRidesBetweenDates() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + SECOND_DRIVER_ID + "/2023-01-01/2023-01-10"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1234)))
                .andExpect(jsonPath("$[1].id", Matchers.is(1235)));
    }
    @Test
    @DisplayName("Test Should Return Empty List For Given Two Dates  /api/ride/passenger/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListBetweenDatesDriver() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + SECOND_DRIVER_ID + "/2023-01-01/2023-01-05"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }

    @Test
    @DisplayName("Test Should Return Empty List If Start and End Date are Same /api/ride/driver/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListOnSameDateDriver() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + SECOND_DRIVER_ID + "/2023-01-01/2023-01-01"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }

    @Test
    @DisplayName("Test Should Return Empty List If End Date Is After Start Date /api/ride/driver/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListIfStartDateIsAfterDateDriver() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + SECOND_DRIVER_ID + "/2023-01-23/2023-01-01"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }


    @Test
    @DisplayName("Test Should Return Not Found Status For Invalid Driver ID  /api/ride/driver/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalidDriverId() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + INVALID_DRIVER_ID + "/2023-01-01/2023-01-31"))
                .andExpect(status().is(404));
    }



    @Test
    @DisplayName("Test Should Return Passenger Rides Between Two Dates  /api/ride/passenger/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnPassengerRidesBetweenDates() throws Exception{
        mockMvc.perform(get("/api/ride/passenger/" + SECOND_PASSENGER_ID + "/2023-01-01/2023-01-11"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1234)))
                .andExpect(jsonPath("$[1].id", Matchers.is(1235)));
    }

    @Test
    @DisplayName("Test Should Return Empty List For Given Two Dates  /api/ride/passenger/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListBetweenDates() throws Exception{
        mockMvc.perform(get("/api/ride/passenger/" + SECOND_PASSENGER_ID + "/2023-01-01/2023-01-05"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }


    @Test
    @DisplayName("Test Should Return Empty List If Start and End Date are Same /api/ride/passenger/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListOnSameDatePassenger() throws Exception{
        mockMvc.perform(get("/api/ride/passenger/" + SECOND_PASSENGER_ID + "/2023-01-01/2023-01-01"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }

    @Test
    @DisplayName("Test Should Return Empty List If End Date Is After Start Date /api/ride/passenger/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListIfStartDateIsAfterDatePassenger() throws Exception{
        mockMvc.perform(get("/api/ride/passenger/" + SECOND_PASSENGER_ID + "/2023-01-23/2023-01-01"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }


    @Test
    @DisplayName("Test Should Return Not Found Status For Invalid Driver ID  /api/ride/passenger/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalidPassengerId() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + INVALID_PASSENGER_ID + "/2023-01-01/2023-01-31"))
                .andExpect(status().is(404));
    }


    @Test
    @DisplayName("Test Should Return All Rides Between Two Dates  /api/ride/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnAllRidesBetweenDates() throws Exception{
        mockMvc.perform(get("/api/ride/2023-01-01/2023-01-11"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1234)))
                .andExpect(jsonPath("$[1].id", Matchers.is(1235)));
    }

    @Test
    @DisplayName("Test Should Return Empty List For Given Two Dates  /api/ride/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListBetweenDatesAll() throws Exception{
        mockMvc.perform(get("/api/ride/2023-01-01/2023-01-05"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }


    @Test
    @DisplayName("Test Should Return Empty List If Start and End Date are Same /api/ride/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListOnSameDateAll() throws Exception{
        mockMvc.perform(get("/api/ride/2023-01-01/2023-01-01"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }

    @Test
    @DisplayName("Test Should Return Empty List If End Date Is After Start Date /api/ride/{startDate}/{endDate}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnEmptyListIfStartDateIsAfterDate() throws Exception{
        mockMvc.perform(get("/api/ride/2023-01-23/2023-01-01"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }


    @Test
    @DisplayName("Test Should Accept Ride /api/ride/{id}/accept")
    @Sql("classpath:data-test.sql")
    public void shouldAcceptRide() throws Exception{
        mockMvc.perform(put("/api/ride/" + VALID_RIDE_ID + "/accept")
                .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status", Matchers.is(RideStatus.ACCEPTED.toString())));

    }

    @Test
    @DisplayName("Test Should Not Accept Ride That Is Not Pending Or Started /api/ride/{id}/accept")
    @Sql("classpath:data-test.sql")
    public void shouldNotWithdrawRideThatIsNotPending() throws Exception{
        mockMvc.perform(put("/api/ride/" + ACTIVE_RIDE_ID + "/accept")
                        .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Cannot accept a ride that is not in status PENDING! \"}"));

    }


    @Test
    @DisplayName("Test Should Return Status Not Found For Invalid Ride /api/ride/{id}/accept")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalid() throws Exception{
        mockMvc.perform(put("/api/ride/" + INVALID_RIDE_ID + "/accept")
                        .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(404))
                .andExpect(content().string("Ride does not exist!"));

    }
    @Test
    @DisplayName("Test Should Return Status Forbidden  Without Access Token /api/ride/{id}/accept")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNoAuthorization() throws Exception{
        mockMvc.perform(put("/api/ride/" + VALID_RIDE_ID + "/accept"))
                .andExpect(status().is(403));

    }


    @Test
    @DisplayName("Test Should Withdraw Ride /api/ride/{id}/withdraw")
    @Sql("classpath:data-test.sql")
    public void shouldWithdrawRide() throws Exception{
        mockMvc.perform(put("/api/ride/" + VALID_RIDE_ID + "/withdraw")
                        .header("Authorization","Bearer " + PASSENGER_TOKEN))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status", Matchers.is(RideStatus.CANCELED.toString())));
    }

    @Test
    @DisplayName("Test Should Not Withdraw Ride That Is Not Pending Or Started /api/ride/{id}/withdraw")
    @Sql("classpath:data-test.sql")
    public void shouldNotAcceptRideThatIsNotPending() throws Exception{
        mockMvc.perform(put("/api/ride/" + ACTIVE_RIDE_ID + "/withdraw")
                        .header("Authorization","Bearer " + PASSENGER_TOKEN))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Cannot cancel a ride that is not in status PENDING or STARTED! \"}"));

    }


    @Test
    @DisplayName("Test Should Return Status Not Found For Invalid Ride /api/ride/{id}/withdraw")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalidWithdraw() throws Exception{
        mockMvc.perform(put("/api/ride/" + INVALID_RIDE_ID + "/withdraw")
                        .header("Authorization","Bearer " + PASSENGER_TOKEN))
                .andExpect(status().is(404));

    }

    @Test
    @DisplayName("Test Should Start Ride /api/ride/{id}/start")
    @Sql("classpath:data-test.sql")
    public void shouldStartRide() throws Exception{
        mockMvc.perform(put("/api/ride/" + ACCEPTED_RIDE_ID + "/start")
                        .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status", Matchers.is(RideStatus.ACTIVE.toString())));
    }

    @Test
    @DisplayName("Test Should Not Start Ride That Is Not Accepted /api/ride/{id}/start")
    @Sql("classpath:data-test.sql")
    public void shouldNotStartRideThatIsNotPending() throws Exception{
        mockMvc.perform(put("/api/ride/" + ACTIVE_RIDE_ID + "/start")
                        .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Cannot start a ride that is not in status ACCEPTED! \"}"));

    }


    @Test
    @DisplayName("Test Should Return Status Not Found For Invalid Ride /api/ride/{id}/start")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalidStart() throws Exception{
        mockMvc.perform(put("/api/ride/" + INVALID_RIDE_ID + "/start")
                        .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(404))
                .andExpect(content().string("Ride does not exist!"));

    }

    @Test
    @DisplayName("Test Should End Ride /api/ride/{id}/end")
    @Sql("classpath:data-test.sql")
    public void shouldEndRide() throws Exception{
        mockMvc.perform(put("/api/ride/" + ACTIVE_RIDE_ID + "/end")
                        .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status", Matchers.is(RideStatus.FINISHED.toString())));
    }

    @Test
    @DisplayName("Test Should Not End Ride That Is Not Active /api/ride/{id}/end")
    @Sql("classpath:data-test.sql")
    public void shouldNotEndRideThatIsNotActive() throws Exception{
        mockMvc.perform(put("/api/ride/" + VALID_RIDE_ID + "/end")
                        .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Cannot end a ride that is not in status ACTIVE! \"}"));

    }

    @Test
    @DisplayName("Test Should Return Status Not Found For Invalid Ride /api/ride/{id}/end")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalidEnd() throws Exception{
        mockMvc.perform(put("/api/ride/" + INVALID_RIDE_ID + "/end")
                        .header("Authorization","Bearer " + DRIVER_TOKEN))
                .andExpect(status().is(404))
                .andExpect(content().string("Ride does not exist!"));

    }

//
//    @Test
//    @DisplayName("Test Should Cancel Ride /api/ride/{id}/cancel")
//    @Sql("classpath:data-test.sql")
//    public void shouldCancelRide() throws Exception{
//        mockMvc.perform(put("/api/ride/" + VALID_RIDE_ID + "/cancel")
//                        .header("Authorization","Bearer " + DRIVER_TOKEN))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("$.status", Matchers.is(RideStatus.CANCELED.toString())));
//    }
//
//    @Test
//    @DisplayName("Test Should Not Cancel Ride That Is Not Active /api/ride/{id}/cancel")
//    @Sql("classpath:data-test.sql")
//    public void shouldNotCancelRideThatIsNotActive() throws Exception{
//        mockMvc.perform(put("/api/ride/" + ACTIVE_RIDE_ID + "/end")
//                        .header("Authorization","Bearer " + DRIVER_TOKEN))
//                .andExpect(status().is(400))
//                .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Cannot cancel a ride that is not in status PENDING or ACCEPTED! \"}"));
//
//    }
//
//    @Test
//    @DisplayName("Test Should Return Status Not Found For Invalid Ride /api/ride/{id}/cancel")
//    @Sql("classpath:data-test.sql")
//    public void shouldReturnNotFoundForInvalidCancel() throws Exception{
//        mockMvc.perform(put("/api/ride/" + INVALID_RIDE_ID + "/cancel")
//                        .header("Authorization","Bearer " + DRIVER_TOKEN))
//                .andExpect(status().is(404))
//                .andExpect(content().string("Ride does not exist!"));
//
//    }

    @Test
    @DisplayName("Test Should Retrieve All Rides")
    @Sql("classpath:data-test.sql")
    public void shouldReturnAllRides() throws Exception {
        mockMvc.perform(get("/api/ride/all"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(8)));
    }



}
