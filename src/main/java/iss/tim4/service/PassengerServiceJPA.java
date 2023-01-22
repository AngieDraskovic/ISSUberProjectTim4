package iss.tim4.service;


import iss.tim4.domain.RideStatus;
import iss.tim4.domain.dto.OneRideOfPassengerDTO;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
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

import java.util.*;

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

    public Set<Passenger> getPassengers(PassengerDTOResult[] passengers) {
        Set<Passenger> passengerSet = new HashSet<>();
        for (PassengerDTOResult passengerDTOResult : passengers){
            Passenger passenger = findOne(passengerDTOResult.getId());
            passengerSet.add(passenger);
        }
        return passengerSet;
    }


    /* Prolazi kroz sve putnike koji ucestvuju u voznji i vraca false ako je neki od njih vec porucio voznju (PENDING) */
    public boolean possibleOrder(RideDTORequest newRide) {
        for (PassengerDTOResult passengerDTOResult : newRide.getPassengers()) {
            Passenger passenger = findOne(passengerDTOResult.getId());
            for (Ride ride : passenger.getRides()) {
                if (ride.getStatus().equals(RideStatus.ACTIVE))
                    return false;
                if (ride.getStatus().equals(RideStatus.PENDING) || ride.getStatus().equals(RideStatus.ACCEPTED)){
                    /* Ako je poruico voznju vec i ponovo porucuje, ali ovaj put za buducnost, vrsi se provjera
                     * da li se ta buduca voznja preklapa sa tom koju je vec porucio. */
                    if (ride.getStartTime().isBefore(newRide.getStartTime().plusMinutes( newRide.getEstimatedTime().longValue()))
                            && newRide.getStartTime().isBefore(ride.getStartTime().plusMinutes((ride.getEstimatedTimeInMinutes().longValue())))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
