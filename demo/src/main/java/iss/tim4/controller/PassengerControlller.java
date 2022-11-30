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
@RequestMapping("/api/putnik")
@AllArgsConstructor
public class PassengerControlller {

    @Autowired
    private PassengerService passengerService;

    // get all
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PassengerDTO>> getPassengers() {
        Collection<PassengerDTO> passengers = passengerService.findAll();
        return new ResponseEntity<Collection<PassengerDTO>>(passengers, HttpStatus.OK);
    }


    // get by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> getPassenger(@PathVariable("id") Long id) {
        PassengerDTO passenger = passengerService.findOne(id);

        if (passenger == null) {
            return new ResponseEntity<PassengerDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PassengerDTO>(passenger , HttpStatus.OK);
    }

    // create
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passenger) throws Exception {
        PassengerDTO savedPassenger = passengerService.create(passenger);
        return new ResponseEntity<PassengerDTO>(savedPassenger, HttpStatus.CREATED);
    }

    // update   --> for now only changes the name of passenger
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> updateDriver(@RequestBody PassengerDTO passenger, @PathVariable Long id)
            throws Exception {
        PassengerDTO passengerForUpdate = passengerService.findOne(id);
        passengerForUpdate.copyValues(passenger);

        PassengerDTO updatedPassenger = passengerService.update(passengerForUpdate);

        if (updatedPassenger == null) {
            return new ResponseEntity<PassengerDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<PassengerDTO>(updatedPassenger, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PassengerDTO> deletePassenger(@PathVariable("id") Long id) {
        passengerService.delete(id);
        return new ResponseEntity<PassengerDTO>(HttpStatus.NO_CONTENT);
    }



}
