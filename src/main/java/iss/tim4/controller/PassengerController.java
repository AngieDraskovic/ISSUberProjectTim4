package iss.tim4.controller;

import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.passenger.PassengerDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOGetAll;
import iss.tim4.domain.dto.passenger.PassengerDTOResponse;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.PassengerActivation;
import iss.tim4.domain.model.Ride;
import iss.tim4.errors.UberException;
import iss.tim4.service.PassengerActivationService;
import iss.tim4.service.PassengerServiceJPA;
import iss.tim4.service.UserServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/passenger")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PassengerController {

    @Autowired
    private PassengerServiceJPA passengerServiceJPA;
    @Autowired
    private PassengerActivationService passengerActivationService;
    @Autowired
    private UserServiceJPA userService;

    // get all - /api/passenger
    @GetMapping
    public ResponseEntity<UberPageDTO<PassengerDTOResult>> getPassengers(Pageable pageable) {
        return new ResponseEntity<>(passengerServiceJPA.getAllPassengers(pageable), HttpStatus.OK);
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

        if(userService.getUser(passengerDTO.getEmail())!=null){
            throw new UberException(HttpStatus.BAD_REQUEST, "User with that email already exists! ");
        }
        if(userService.getUserByTelephoneNumber(passengerDTO.getTelephoneNumber())!=null){
            throw new UberException(HttpStatus.BAD_REQUEST, "User with that telephone number already exists! ");
        }
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<MsgDTO> activatePassenger(@PathVariable("activationId") Integer activationId) throws UberException {
        PassengerActivation passengerForActivation = passengerActivationService.findOne(activationId);
        if (passengerForActivation == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "Activation with entered id does not exist! ");
        }
        if(passengerActivationService.hasActivationExpired(activationId)){
            throw new UberException(HttpStatus.BAD_REQUEST, "Activation expired. Register again!");
        }
        Passenger p = passengerForActivation.getPassenger();
        p.setActive(true);
        MsgDTO msgDTO = new MsgDTO("Successful account activation!");
        return new ResponseEntity<MsgDTO>(msgDTO, HttpStatus.OK);
    }


    //get passengers rides -> /api/passenger/1/ride
    @GetMapping(value = "/{id}/ride")
    public ResponseEntity<UberPageDTO<OneRideOfPassengerDTO>> getPassengerRides(@PathVariable("id") Integer id, Pageable pageable) {
        Passenger passenger = passengerServiceJPA.findOne(id);
        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(passengerServiceJPA.getRidesOfPassenger(pageable, id), HttpStatus.OK);
    }





}
