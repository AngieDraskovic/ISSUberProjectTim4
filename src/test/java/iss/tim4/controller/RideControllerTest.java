package iss.tim4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.LocationDTO;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.model.Ride;
import iss.tim4.domain.model.Role;
import iss.tim4.security.jwt.JwtTokenUtil;
import iss.tim4.service.*;
import org.hamcrest.Matchers;
import org.jboss.jandex.JandexAntTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private static final Integer PASSENGER_ID = 111;
    private static final Integer INVALID_PASSENGER_ID = 543;

    private static final Integer INVALID_DRIVER_ID = 987;

    private static final Integer DRIVER_ID = 222;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @BeforeMethod
    public void setUp(){
        // TODO mozda pripremiti tokene za sve uloge

    }

    @Test
    @DisplayName("Test Should Retrieve Ride By Id When Making GET Request To Endpoint - /api/ride/{id}")
    @Sql("classpath:data-test.sql")
    public void shouldGetRideById() throws Exception {
        mockMvc.perform(get("/api/ride/" + VALID_RIDE_ID))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(VALID_RIDE_ID)))
                .andExpect(jsonPath("$.totalCost", Matchers.is(1503.01)))
                .andExpect(jsonPath("$.estimatedTimeInMinutes", Matchers.is(102.2)))
                .andExpect(jsonPath("$.kilometers", Matchers.is(0.9)))
                .andExpect(jsonPath("$.petTransport", Matchers.is(false)))
                .andExpect(jsonPath("$.babyTransport", Matchers.is(true)));
        //TODO: da li je potrebno ovdje provjeravati i vozaca i passengera ili je ovo dovoljno
    }


    @Test
    @DisplayName("Test Should Have Status Not Found For Getting Ride With Invalid ID - /api/ride/{id}")
    @Sql("classpath:data-test.sql")
    public void shouldReturnRideNotFound() throws Exception {
        mockMvc.perform(get("/api/ride/" + INVALID_RIDE_ID))
                .andExpect(status().is(404))
                .andExpect(content().string("Ride does not exist"));

    }

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

    // TODO: implement more test cases for posting ride


    @Test
    @DisplayName("Test Should Retrieve Passenger Ride History /api/ride/passenger/rideHistory")
    @Sql("classpath:data-test.sql")
    public void shouldFindPassengerRideHistory() throws Exception{
        mockMvc.perform(get("/api/ride/passenger/" + PASSENGER_ID + "/rideHistory"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].totalCost", Matchers.is(1503.01)));

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
                .andExpect(jsonPath("$[0].totalCost", Matchers.is(1503.01)));

    }

    @Test
    @DisplayName("Test Should Return Not Found Status For Invalid Driver ID /api/ride/driver/rideHistory")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalidDriver() throws Exception{
        mockMvc.perform(get("/api/ride/driver/" + INVALID_DRIVER_ID + "/rideHistory"))
                .andExpect(status().is(404));

    }

    @Test
    @DisplayName("Test Should Accept Ride /api/ride/{id}/accept")
    @Sql("classpath:data-test.sql")
    public void shouldAcceptRide() throws Exception{
        String token = jwtTokenUtil.generateToken("driver-test@gmail.com", Role.DRIVER, 222);
        mockMvc.perform(put("/api/ride/" + VALID_RIDE_ID + "/accept")
                .header("Authorization","Bearer " + token))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status", Matchers.is(RideStatus.ACCEPTED.toString())));


    }


    @Test
    @DisplayName("Test Should Return Status Not Found For Invalid Ride /api/ride/{id}/accept")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNotFoundForInvalid() throws Exception{
        String token = jwtTokenUtil.generateToken("driver-test@gmail.com", Role.DRIVER, 222);
        mockMvc.perform(put("/api/ride/" + INVALID_RIDE_ID + "/accept")
                        .header("Authorization","Bearer " + token))
                .andExpect(status().is(404));

    }



    @Test
    @DisplayName("Test Should Return Status Forbidden  Without Access Token /api/ride/{id}/accept")
    @Sql("classpath:data-test.sql")
    public void shouldReturnNoAuthorization() throws Exception{
        String token = jwtTokenUtil.generateToken("driver-test@gmail.com", Role.DRIVER, 222);
        mockMvc.perform(put("/api/ride/" + VALID_RIDE_ID + "/accept"))
                .andExpect(status().is(403));

    }


}
