package iss.tim4.service;

import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.model.Passenger;
import iss.tim4.repository.PassengerRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PassengerServiceJPA {

    @Autowired
    private PassengerRepositoryJPA passengerRepositoryJPA;

    public Passenger findOne(Integer id) {
        return passengerRepositoryJPA.findById(id).orElseGet(null);
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

    public Set<Passenger> getPassengers(PassengerDTOResult[] passengers) {
        Set<Passenger> passengerSet = new HashSet<>();
        for (PassengerDTOResult passengerDTOResult : passengers){
            Passenger passenger = findOne(passengerDTOResult.getId());
            passengerSet.add(passenger);
        }
        return passengerSet;
    }


}
