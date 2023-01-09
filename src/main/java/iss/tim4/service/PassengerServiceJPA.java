package iss.tim4.service;

import iss.tim4.domain.dto.OneRideOfPassengerDTO;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Ride;
import iss.tim4.domain.model.Role;
import iss.tim4.domain.model.User;
import iss.tim4.repository.PassengerRepositoryJPA;
import iss.tim4.repository.RideRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerServiceJPA {

    @Autowired
    private PassengerRepositoryJPA passengerRepositoryJPA;
    @Autowired
    private RideRepositoryJPA rideRepositoryJPA;
    public Passenger findOne(Integer id) {
        return passengerRepositoryJPA.findById(id).orElse(null);
    }

    public List<Passenger> findAll() {
        return passengerRepositoryJPA.findAll();
    }

    public Page<Passenger> findAll(Pageable page) {
        return passengerRepositoryJPA.findAll(page);
    }

    public Passenger save(Passenger passenger) {
        return passengerRepositoryJPA.save(passenger);
    }

    public void remove(Integer id) {
        passengerRepositoryJPA.deleteById(id);
    }
    public UberPageDTO<PassengerDTOResult> getAllPassengers(Pageable pageable) {
        return new UberPageDTO<>(passengerRepositoryJPA.findAll(pageable).map(PassengerDTOResult::new));
    }
    public UberPageDTO<OneRideOfPassengerDTO> getRidesOfPassenger(Pageable pageable, Integer userId) {
        Passenger passenger = findOne(userId);
        return new UberPageDTO<>(rideRepositoryJPA.findByPassengersId(pageable, userId).map(OneRideOfPassengerDTO::new));

    }
}
