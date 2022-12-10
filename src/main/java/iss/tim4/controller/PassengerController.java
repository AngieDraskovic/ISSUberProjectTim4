package iss.tim4.controller;

import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.passenger.PassengerDTOGetAll;
import iss.tim4.domain.dto.passenger.PassengerDTOResponse;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.PassengerActivation;
import iss.tim4.domain.model.Ride;
import iss.tim4.service.PassengerActivationService;
import iss.tim4.service.PassengerServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/passenger")
@AllArgsConstructor
public class PassengerController {

    @Autowired
    private PassengerServiceJPA passengerServiceJPA;
    @Autowired
    private PassengerActivationService passengerActivationService;

    // get all - /api/passenger
    @GetMapping
    public ResponseEntity<PassengerDTOGetAll> getPassengers() {
        List<Passenger> passengers = passengerServiceJPA.findAll();
        int totalCount = passengers.size();

        PassengerDTOResult[] results = new PassengerDTOResult[totalCount];
        for(int i = 0; i < passengers.size(); i++) {
            results[i] = new PassengerDTOResult(passengers.get(i));
        }
        PassengerDTOGetAll passengerDTORequest = new PassengerDTOGetAll(results, totalCount);
        return new ResponseEntity<>(passengerDTORequest, HttpStatus.OK);

    }


    // get by id - /api/passenger/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<PassengerDTOResult> getPassenger(@PathVariable("id") Integer id) {
        Passenger passenger = passengerServiceJPA.findOne(id);

        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PassengerDTOResult>(new PassengerDTOResult(passenger) , HttpStatus.OK);
    }


    // create /api/passenger
    @PostMapping(consumes = "application/json")
    public ResponseEntity<PassengerDTOResult> createPassenger(@RequestBody PassengerDTOResponse passengerDTO) throws Exception {
        Passenger passenger = new Passenger();
        passenger.setName(passengerDTO.getName());
        passenger.setSurname(passengerDTO.getSurname());
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setPassword(passengerDTO.getPassword());
        passenger.setTelephoneNumber(passengerDTO.getTelephoneNumber());
        passenger.setAddress(passengerDTO.getAddress());
        passenger.setProfilePicture(passengerDTO.getProfilePicture());
        passenger.setActive(false);
        passenger.setBlocked(false);
        passenger = passengerServiceJPA.save(passenger);
        return new ResponseEntity<>(new PassengerDTOResult(passenger), HttpStatus.OK);  // it should be created......
    }

    // update   --> /api/passenger/1
    @PutMapping(value = "/{id}")
    public ResponseEntity<PassengerDTOResult> updatePassenger(@RequestBody PassengerDTOResponse passengerDTO, @PathVariable Integer id)
            throws Exception {
        Passenger passengerForUpdate = passengerServiceJPA.findOne(id);
        if (passengerForUpdate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(passengerDTO.getName() != null){
            passengerForUpdate.setName(passengerDTO.getName());
        }
        if(passengerDTO.getSurname() != null){
            passengerForUpdate.setSurname(passengerDTO.getSurname());
        }
        if(passengerDTO.getProfilePicture() != null){
            passengerForUpdate.setProfilePicture(passengerDTO.getProfilePicture());
        }
        if(passengerDTO.getTelephoneNumber() != null){
            passengerForUpdate.setTelephoneNumber(passengerDTO.getTelephoneNumber());
        }
        if(passengerDTO.getPassword() != null){
            passengerForUpdate.setPassword(passengerDTO.getPassword());
        }
        if(passengerDTO.getAddress() != null){
            passengerForUpdate.setAddress(passengerDTO.getAddress());
        }
        if(passengerDTO.getEmail() != null) {
            passengerForUpdate.setEmail(passengerDTO.getEmail());
        }

        passengerForUpdate = passengerServiceJPA.save(passengerForUpdate);

        if (passengerForUpdate== null) {
            return new ResponseEntity<PassengerDTOResult>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<PassengerDTOResult>(new PassengerDTOResult(passengerForUpdate), HttpStatus.OK);
    }


    @GetMapping(value="/activate/{activationId}")
    public ResponseEntity<Void> activatePassenger(@PathVariable("activationId") Integer activationId){
        PassengerActivation passengerForActivation = passengerActivationService.findOne(activationId);

        if (passengerForActivation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Passenger p = passengerForActivation.getPassenger();
        p.setActive(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //get passengers rides -> /api/passenger/1/ride
    @GetMapping(value = "/{id}/ride")
    public ResponseEntity<RidesOfPassengerDTO> getPassengerRides(@PathVariable("id") Integer id) {
        Passenger passenger = passengerServiceJPA.findOne(id);
        Set<Ride> rides = passenger.getRides();
        int totalCount = rides.size();

        OneRideOfPassengerDTO[] results = new OneRideOfPassengerDTO[totalCount];
        int iter = 0;
        for(Ride ride : rides){
            results[iter] = new OneRideOfPassengerDTO(ride);
            iter++;
        }
        RidesOfPassengerDTO ridesOfPassengerDTO = new RidesOfPassengerDTO(results, totalCount);
        return new ResponseEntity<>(ridesOfPassengerDTO, HttpStatus.OK);
    }




}
