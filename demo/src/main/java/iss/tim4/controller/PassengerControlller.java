package iss.tim4.controller;

import iss.tim4.domain.dto.PassengerDTO;
import iss.tim4.service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/passenger")
@AllArgsConstructor
public class PassengerControlller {

    @Autowired
    private PassengerService passengerService;

    // get all - /api/passenger
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PassengerDTO>> getPassengers() {
        Collection<PassengerDTO> passengers = passengerService.findAll();
        return new ResponseEntity<Collection<PassengerDTO>>(passengers, HttpStatus.OK);
    }


    // get by id - /api/passenger/1
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> getPassenger(@PathVariable("id") Long id) {
        PassengerDTO passenger = passengerService.findOne(id);

        if (passenger == null) {
            return new ResponseEntity<PassengerDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PassengerDTO>(passenger , HttpStatus.OK);
    }

    // create /api/passenger
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passenger) throws Exception {
        PassengerDTO savedPassenger = passengerService.create(passenger);
        return new ResponseEntity<PassengerDTO>(savedPassenger, HttpStatus.CREATED);
    }

    //TODO: /api/passenger{activationId}
    @PostMapping(value="{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> activatePassenger(@RequestBody PassengerDTO passenger) throws Exception {
        PassengerDTO savedPassenger = passengerService.create(passenger);
        return new ResponseEntity<PassengerDTO>(savedPassenger, HttpStatus.CREATED);
    }

    // update   --> /api/passenger/1
    // radi i ukoliko proslijedimo samo ona polja koja zelimo da promjenimo
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> updateDriver(@RequestBody PassengerDTO passenger, @PathVariable Long id)
            throws Exception {
        PassengerDTO passengerForUpdate = passengerService.findOne(id);
        // passenger -> the one sent by the request
        // passengerForUpdate -> the one we are updating
       if(passenger.getName() != null){
            passengerForUpdate.setName(passenger.getName());
        }
        if(passenger.getSurname() != null){
            passengerForUpdate.setSurname(passenger.getSurname());
        }
        if(passenger.getProfilePicture() != null){
            passengerForUpdate.setProfilePicture(passenger.getProfilePicture());
        }
        if(passenger.getTelephoneNumber() != null){
            passengerForUpdate.setTelephoneNumber(passenger.getTelephoneNumber());
        }
        if(passenger.getPassword() != null){
            passengerForUpdate.setPassword(passenger.getPassword());
        }
        if(passenger.getAddress() != null){
            passengerForUpdate.setAddress(passenger.getAddress());
        }
        if(passenger.getEmail() != null) {
            passengerForUpdate.setEmail(passenger.getEmail());
        }
        PassengerDTO updatedPassenger = passengerService.update(passengerForUpdate); // TODO:provjeri ovaj update

        if (updatedPassenger == null) {
            return new ResponseEntity<PassengerDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<PassengerDTO>(updatedPassenger, HttpStatus.OK);
    }


    // get passengers rides -> /api/passenger/1/ride
    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> getPassengerRides(@PathVariable("id") Long id) {
        PassengerDTO passenger = passengerService.findOne(id);

        if (passenger == null) {
            return new ResponseEntity<PassengerDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PassengerDTO>(passenger , HttpStatus.OK);
    }





}
