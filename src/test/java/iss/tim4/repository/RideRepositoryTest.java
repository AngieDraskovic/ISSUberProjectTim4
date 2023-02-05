package iss.tim4.repository;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Ride;

import iss.tim4.domain.model.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RideRepositoryTest {


    private static final Integer PASSENGER_ID = 111;
    private static final Integer DRIVER_ID = 222;

    @Autowired
    private RideRepositoryJPA rideRepository;

    @Test
    @DisplayName("Test Should Save New Ride")
    public void shouldSaveRide(){
        // arrange
        Ride ride = new Ride(LocalDateTime.now(), LocalDateTime.now(), 500.0, 5.0,5.0, RideStatus.PENDING, true, true );

        // act
        Ride savedRide = rideRepository.save(ride);

        // assert
        assertThat(savedRide).usingRecursiveComparison().ignoringFields("rideId").isEqualTo(ride);
    }

    @Test
    @Sql("classpath:data-test.sql")
    @DisplayName("Test Should Save Ride Through Sql File And Find Rides By Driver ID")
    public void shouldSaveTroughSqlAndFindByDriver(){
        // act
        List<Ride> rides = rideRepository.findByDriverId(DRIVER_ID);
        //assert
        assertThat(rides).isNotEmpty();
        assertThat(rides.size()).isEqualTo(1);
        assertThat(rides.get(0).getDriver().getId()).isEqualTo(DRIVER_ID);
        assertThat(rides.get(0).getDriver().getEmail()).isEqualTo("driver-test@gmail.com");

    }


    @Test
    @DisplayName("Test Should Save Ride Through Sql File And Find Rides By Passenger ID")
    @Sql("classpath:data-test.sql")
    public void shouldSaveTroughSqlAndFindByPassenger(){
        // act
        List<Ride> rides = rideRepository.findByPassengerId(PASSENGER_ID);

        //assert
        assertThat(rides).isNotEmpty();
        assertThat(rides.size()).isEqualTo(1);
        assertThat(rides.get(0).getPassengers().size()).isEqualTo(1);
    }


    @Test
    @Sql("classpath:data-test.sql")
    public void shouldGetRidesBasedOnRoutes(){
        //arrange
        List<Integer> responseIds = new ArrayList<>();
        List<Integer> rideIds = new ArrayList<>(){};
        rideIds.add(1);
        rideIds.add(2);
        rideIds.add(3);
        rideIds.add(4);
        rideIds.add(5);
        //act
        List<Object[]> ridesFromRoutes = rideRepository.getRidesFromRoutes();
        for(Object[] o : ridesFromRoutes){
            responseIds.add(Integer.parseInt(o[0].toString()));
        }
        //assert
        assertThat(ridesFromRoutes).isNotEmpty();
        assertThat(responseIds).isEqualTo(rideIds);
    }
}
