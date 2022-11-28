package iss.tim4.controller;

import iss.tim4.domain.Passenger;
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
    public ResponseEntity<Collection<Passenger>> getPassengers() {
        Collection<Passenger> passengers = passengerService.findAll();
        return new ResponseEntity<Collection<Passenger>>(passengers, HttpStatus.OK);
    }


    // get by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Passenger> getPassenger(@PathVariable("id") Long id) {
        Passenger passenger = passengerService.findOne(id);

        if (passenger == null) {
            return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Passenger>(passenger , HttpStatus.OK);
    }

    // create
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) throws Exception {
        Passenger savedPassenger = passengerService.create(passenger);
        return new ResponseEntity<Passenger>(savedPassenger, HttpStatus.CREATED);
    }

    // update   --> for now only changes the name of passenger
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Passenger> updateDriver(@RequestBody Passenger passenger, @PathVariable Long id)
            throws Exception {
        Passenger passengerForUpdate = passengerService.findOne(id);
        passengerForUpdate.copyValues(passenger);

        Passenger updatedPassenger = passengerService.update(passengerForUpdate);

        if (updatedPassenger == null) {
            return new ResponseEntity<Passenger>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Passenger>(updatedPassenger, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Passenger> deletePassenger(@PathVariable("id") Long id) {
        passengerService.delete(id);
        return new ResponseEntity<Passenger>(HttpStatus.NO_CONTENT);
    }



}
