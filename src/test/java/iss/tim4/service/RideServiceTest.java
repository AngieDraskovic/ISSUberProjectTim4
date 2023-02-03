package iss.tim4.service;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.OneRideOfPassengerDTO;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.domain.model.Ride;
import iss.tim4.repository.RideRepositoryJPA;
import org.assertj.core.api.Assertions;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class
RideServiceTest {


    private static final Integer PASSENGER_ID = 1;
    private static final Integer INVALID_PASSENGER_ID = 111;
    @Autowired
    private RideServiceJPA rideService;

    @MockBean
    private RideRepositoryJPA rideRepository;

    @MockBean
    private VehicleTypeServiceJPA vehicleTypeService;

    @Captor
    private ArgumentCaptor<Ride> rideArgumentCaptor;


    @Test
    @DisplayName("Test Should Retrieve Ride By Id")
    public void shouldFindRideById() {
        Ride ride = new Ride(123, LocalDateTime.now(), LocalDateTime.now(), 500.0, 5.0,5.0, RideStatus.PENDING, true, true );
        Mockito.when(rideRepository.findById(123)).thenReturn(Optional.of(ride));

        Ride actualRide = rideService.findOne(123);

        verify(rideRepository).findById(123);

        Assertions.assertThat(actualRide.getId()).isEqualTo(ride.getId());
        Assertions.assertThat(actualRide.getStartTime()).isEqualTo(ride.getStartTime());
        Assertions.assertThat(actualRide.getEndTime()).isEqualTo(ride.getEndTime());
        Assertions.assertThat(actualRide.getTotalCost()).isEqualTo(ride.getTotalCost());
        Assertions.assertThat(actualRide.getEstimatedTimeInMinutes()).isEqualTo(ride.getEstimatedTimeInMinutes());
        Assertions.assertThat(actualRide.getKilometers()).isEqualTo(ride.getKilometers());
        Assertions.assertThat(actualRide.getStatus()).isEqualTo(ride.getStatus());
    }

    @Test
    @DisplayName("Test Should Return Null If Ride ID does not exist")
    public void shouldNotFindRideThatDoesntExist() {
        Mockito.when(rideRepository.findById(123)).thenReturn(Optional.empty());

        Ride ride = rideService.findOne(123);
        verify(rideRepository).findById(123);

        Assertions.assertThat(ride).isEqualTo(null);
    }

    @Test
    @DisplayName("Test Should Throw An Exception When Ride ID Is Null")
    public void shouldThrowAnExceptionWithIllegalRideId() {
        Mockito.when(rideRepository.findById(null)).thenAnswer(invocation -> {
            throw new NullPointerException();
        });

        assertThrows(NullPointerException.class, (Executable) () -> rideService.findOne(null));
    }

// --------
    @Test
    @DisplayName("Test Should Find All Rides")
    public void shouldFindAllRides() {
        List<Ride> rides = Arrays.asList(new Ride( LocalDateTime.now(), LocalDateTime.now(), 500.0, 5.0,5.0, RideStatus.PENDING, true, true),
                new Ride( LocalDateTime.now(), LocalDateTime.now(), 300.0, 2.0,2.0, RideStatus.PENDING, false, false ));
        Mockito.when(rideRepository.findAll()).thenReturn(rides);
        List<Ride> result = rideService.findAll();

        verify(rideRepository).findAll();

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result).isEqualTo(rides);
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test Should Retrieve Empty List When Repository Is Empty")
    public void shouldRetrieveEmptyList() {
        List<Ride> rides = new ArrayList<>();
        Mockito.when(rideRepository.findAll()).thenReturn(rides);
        List<Ride> result = rideService.findAll();

        verify(rideRepository).findAll();

        Assertions.assertThat(result).isEmpty();
        Assertions.assertThat(result).isEqualTo(rides);
    }

    @Test
    @DisplayName("Test Should Find All Rides Pageable")
    public void shouldFindAllRidesPageable() {
        Pageable pageable = mock(Pageable.class);
        Page<Ride> expectedPage = mock(Page.class);
        Mockito.when(rideRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Ride> result = rideService.findAll(pageable);

        verify(rideRepository).findAll(pageable);

        Assertions.assertThat(result).isEqualTo(expectedPage);

    }

    @Test
    @DisplayName("Test Should Retrieve Null Value With Null Pageable")
    public void shouldTestWithNullPageable() {
        Pageable pageable = null;
        Mockito.when(rideRepository.findAll(pageable)).thenReturn(null);
        Page<Ride> result = rideService.findAll(pageable);
        Assertions.assertThat(result).isNull();
    }

// ----------
    @Test
    @DisplayName("Test Should Find All Rides Passenger Has")
    public void shouldFindPassengersRides(){
        List<Ride> rides = Arrays.asList(new Ride( LocalDateTime.now(), LocalDateTime.now(), 500.0, 5.0,5.0, RideStatus.PENDING, true, true),
                                        new Ride( LocalDateTime.now(), LocalDateTime.now(), 300.0, 2.0,2.0, RideStatus.PENDING, false, false ));
        Mockito.when(rideRepository.findByPassengerId(PASSENGER_ID)).thenReturn(rides);
        List<Ride> result = rideService.findByPassengerId(PASSENGER_ID);

        verify(rideRepository).findByPassengerId(PASSENGER_ID);

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result).isEqualTo(rides);
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test Should Retrieve An Empty List When Passenger ID Does Not Exist")
    public void shouldRetrieveEmptyListForInvalidId(){
        Mockito.when(rideRepository.findByPassengerId(INVALID_PASSENGER_ID)).thenReturn(new ArrayList<>());

        List<Ride> result = rideService.findByPassengerId(INVALID_PASSENGER_ID);

        verify(rideRepository).findByPassengerId(INVALID_PASSENGER_ID);

        Assertions.assertThat(result).isEmpty();

    }


    @Test
    @DisplayName("Test Should Throw An Exception When Passenger ID Is Null")
    public void shouldThrowExceptionPassengersRides(){
        Mockito.when(rideRepository.findByPassengerId(null)).thenAnswer(invocation -> {
            throw new NullPointerException();
        });

        assertThrows(NullPointerException.class, (Executable) () -> rideService.findByPassengerId(null));

    }


    // ----------

    @Test
    @DisplayName("Test Should Save New Ride ")
    public void shouldSaveNewRide(){
        Ride ride = new Ride(123, LocalDateTime.now(), LocalDateTime.now(), 500.0, 5.0,5.0, RideStatus.PENDING, true, true );
        rideService.save(ride);
        verify(rideRepository, times(1)).save(rideArgumentCaptor.capture());
        Assertions.assertThat(rideArgumentCaptor.getValue().getId()).isEqualTo(123);
    }


    @Test
    @DisplayName("Test Should Throw Exception For Saving Null Ride ")
    public void shouldThrowExceptionSaveNull(){
        Mockito.when(rideRepository.save(null)).thenAnswer(invocation -> {
            throw new NullPointerException();
        });

        assertThrows(NullPointerException.class, (Executable) () -> rideService.save(null));
    }

    @Test
    @DisplayName("Test Should Throw Exception For When Saving Invalid Ride ")
    public void shouldThrowExceptionSavingInvalidRide(){
        Ride ride = new Ride();
        Mockito.when(rideRepository.save(ride)).thenAnswer(invocation -> {
            throw new  ConstraintViolationException("Values of ride cannot be null", null, null, null);
        });

        assertThrows(ConstraintViolationException.class, (Executable) () -> rideService.save(ride));
    }


    // ----------


    // testing calculating ride cost
    @Test
    @DisplayName("Test Should Calculate The Cost Of Ride With Valid Data")
    public void shouldCalculateCost(){
        RideDTORequest rideDTO = new RideDTORequest(VehicleName.STANDARD, true, true, new PassengerDTOResult[]{},new RouteDTO[]{}, LocalDateTime.now(), 5.0, 1.0,1);

        Mockito.when(vehicleTypeService.getPriceForVehicleType(rideDTO.getVehicleType())).thenReturn(10.0);
        double expectedCost = 1200.0;

        double result = rideService.calculateCost(rideDTO);

        verify(vehicleTypeService).getPriceForVehicleType(VehicleName.STANDARD);

        Assertions.assertThat(result).isEqualTo(expectedCost);
    }

    @Test
    @DisplayName("Test Should Calculate Cost With Zero Kilometers")
    public void shoudCalculateCostWithZeroKilometers() {

        RideDTORequest rideDTO = new RideDTORequest(VehicleName.STANDARD, true, true, new PassengerDTOResult[]{},new RouteDTO[]{}, LocalDateTime.now(), 5.0, 0.0, 1);
        double expectedCost = 0.0;
        Mockito.when(vehicleTypeService.getPriceForVehicleType(rideDTO.getVehicleType())).thenReturn(10.0);

        double result = rideService.calculateCost(rideDTO);

        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Test Should Throw An Exception When Calculating Cost With Negative Kilometers")
    public void shouldCalculateCostWithNegativeKilometers() {

        RideDTORequest rideDTO = new RideDTORequest(VehicleName.STANDARD, true, true, new PassengerDTOResult[]{},new RouteDTO[]{}, LocalDateTime.now(), 5.0, -1.0,1);

        assertThrows(IllegalArgumentException.class, (Executable) () -> rideService.calculateCost(rideDTO));

    }

    @Test
    @DisplayName("Test Should Return Cost Of Ride For Luxury Vehicle Type")
    public void shouldCalculateCostWithDifferentVehicleType() {
        RideDTORequest rideDTO = new RideDTORequest(VehicleName.LUXURY, true, true, new PassengerDTOResult[]{},new RouteDTO[]{}, LocalDateTime.now(), 5.0, 1.0, 1);
        double expectedCost = 2400.0;
        Mockito.when(vehicleTypeService.getPriceForVehicleType(rideDTO.getVehicleType())).thenReturn(20.0);

        double result = rideService.calculateCost(rideDTO);

        Assertions.assertThat(result).isEqualTo(expectedCost);

    }

    @Test
    @DisplayName("Test Should Throw An Exception When Vehicle Type Is Null")
    public void shouldThrowAnExceptionWhenTypeIsNull() {
        RideDTORequest rideDTO = new RideDTORequest(null, true, true, new PassengerDTOResult[]{},new RouteDTO[]{}, LocalDateTime.now(), 5.0, 1.0, 1);

        Mockito.when(vehicleTypeService.getPriceForVehicleType(null)).thenAnswer(invocation -> {
                    throw new IllegalArgumentException();
                });
        assertThrows(IllegalArgumentException.class, (Executable) () -> rideService.calculateCost(rideDTO));


    }


    // testing filtering rides by date using list
    public List<Ride> createTestRides(){
        List<Ride> testRides = new ArrayList<>();
        Ride ride1 = new Ride(LocalDateTime.of(2023, 1, 15, 10, 30),
                LocalDateTime.of(2023, 1, 15, 10, 33),
                500.0, 5.0,5.0, RideStatus.PENDING, true, true );
        Ride ride2 = new Ride(LocalDateTime.of(2023, 1, 20, 10, 30),
                LocalDateTime.of(2023, 1, 20, 10, 33),
                500.0, 5.0,5.0, RideStatus.PENDING, true, true );
        Ride rideOutsideOfRange = new Ride(LocalDateTime.of(2023, 1, 25, 10, 30),
                LocalDateTime.of(2023, 1, 25, 10, 33),
                500.0, 5.0,5.0, RideStatus.PENDING, true, true );

        testRides.add(ride1);
        testRides.add(ride2);
        testRides.add(rideOutsideOfRange);
        return testRides;
    }
    @Test
    @DisplayName("Test Should Return Correct Number of Rides in Given Date Range")
    public void shouldReturnCorrectNumberfRides(){
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 22, 23, 59);
        List<Ride> rides = createTestRides();

        List<Ride> result = rideService.filterListOfRidesByDate(startDate, endDate, rides);
        for (Ride ride : result) {
            Assertions.assertThat(ride.getStartTime()).isAfterOrEqualTo(startDate);
            Assertions.assertThat(ride.getStartTime()).isBeforeOrEqualTo(endDate);
        }

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(2);

    }


    @Test
    @DisplayName("Test Should Return Correct Rides in Given Date Range")
    public void shouldReturnCorrectRidesBetweenDates(){
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 22, 23, 59);
        List<Ride> rides = createTestRides();

        List<Ride> result = rideService.filterListOfRidesByDate(startDate, endDate, rides);
        for (Ride ride : result) {
            Assertions.assertThat(ride.getStartTime()).isAfterOrEqualTo(startDate);
            Assertions.assertThat(ride.getStartTime()).isBeforeOrEqualTo(endDate);
        }
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result).containsExactlyInAnyOrder(rides.get(0), rides.get(1));
    }

    @Test
    @DisplayName("Test Should Return Empty List If Start and End Date are Same")
    public void shouldReturnEmptyListIfStartAndEndDateAreSame() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        List<Ride> rides = createTestRides();

        List<Ride> result = rideService.filterListOfRidesByDate(startDate, endDate, rides);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test Should Return Empty List If Start Date is After End Date")
    public void shouldReturnEmptyListIfStartDateIsAfterEndDate() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 31, 23, 59);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        List<Ride> rides = createTestRides();

        List<Ride> result = rideService.filterListOfRidesByDate(startDate, endDate, rides);

        Assertions.assertThat(result).isEmpty();

    }

    @Test
    @DisplayName("Test Should Return Empty List When Rides List Is Empty")
    public void shouldReturnEmptyListWhenRidesListIsEmpty() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 25, 23, 59);
        List<Ride> rides = new ArrayList<>();

        List<Ride> result = rideService.filterListOfRidesByDate(startDate, endDate, rides);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test Should Throw Exception When Start Date and End Date are Null")
    public void shouldReturnEmptyListWhenStartDateAndEndDateAreNull() {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        List<Ride> rides = createTestRides();

        assertThrows(NullPointerException.class, (Executable) () -> rideService.filterListOfRidesByDate(startDate, endDate, rides));

    }


    // testing filtering rides by date using set
    public Set<Ride> createSetOfTestRides(){
        Set<Ride> testRides = new HashSet<>();
        Ride ride1 = new Ride(LocalDateTime.of(2023, 1, 15, 10, 30),
                LocalDateTime.of(2023, 1, 15, 10, 33),
                500.0, 5.0,5.0, RideStatus.PENDING, true, true );
        Ride ride2 = new Ride(LocalDateTime.of(2023, 1, 20, 10, 30),
                LocalDateTime.of(2023, 1, 20, 10, 33),
                500.0, 5.0,5.0, RideStatus.PENDING, true, true );
        Ride rideOutsideOfRange = new Ride(LocalDateTime.of(2023, 1, 25, 10, 30),
                LocalDateTime.of(2023, 1, 25, 10, 33),
                500.0, 5.0,5.0, RideStatus.PENDING, true, true );

        testRides.add(ride1);
        testRides.add(ride2);
        testRides.add(rideOutsideOfRange);
        return testRides;
    }

    @Test
    @DisplayName("Test Should Return Correct Number of Rides From Set in Given Date Range")
    public void shouldReturnCorrectNumberfRidesFromSet(){
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 22, 23, 59);
        Set<Ride> rides = createSetOfTestRides();

        List<Ride> result = rideService.filterRidesByDate(startDate, endDate, rides);
        for (Ride ride : result) {
            Assertions.assertThat(ride.getStartTime()).isAfterOrEqualTo(startDate);
            Assertions.assertThat(ride.getStartTime()).isBeforeOrEqualTo(endDate);
        }

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }


    @Test
    @DisplayName("Test Should Return Correct Rides From Set in Given Date Range")
    public void shouldReturnCorrectRidesFromSetBetweenDates(){
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 22, 23, 59);
        Set<Ride> rides = createSetOfTestRides();

        List<Ride> result = rideService.filterRidesByDate(startDate, endDate, rides);
        for (Ride ride : result) {
            Assertions.assertThat(ride.getStartTime()).isAfterOrEqualTo(startDate);
            Assertions.assertThat(ride.getStartTime()).isBeforeOrEqualTo(endDate);
        }
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("Test Should Return Empty List If Start and End Date are Same")
    public void shouldReturnEmptyListIfStartAndEndDateAreSameSet() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        Set<Ride> rides = createSetOfTestRides();

        List<Ride> result = rideService.filterRidesByDate(startDate, endDate, rides);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test Should Return Empty List If Start Date is After End Date")
    public void shouldReturnEmptyListIfStartDateIsAfterEndDateSet() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 31, 23, 59);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        Set<Ride> rides = createSetOfTestRides();

        List<Ride> result = rideService.filterRidesByDate(startDate, endDate, rides);

        Assertions.assertThat(result).isEmpty();

    }

    @Test
    @DisplayName("Test Should Return Empty List When Ride Set Is Empty")
    public void shouldReturnEmptyListWhenRidesSetIsEmpty() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 25, 23, 59);
        Set<Ride> rides = new HashSet<>();

        List<Ride> result = rideService.filterRidesByDate(startDate, endDate, rides);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test Should Throw Exception When Start Date and End Date are Null")
    public void shouldReturnEmptyListWhenStartDateAndEndDateAreNullSet() {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        Set<Ride> rides = createSetOfTestRides();

        assertThrows(NullPointerException.class, (Executable) () -> rideService.filterRidesByDate(startDate, endDate, rides));

    }





}
