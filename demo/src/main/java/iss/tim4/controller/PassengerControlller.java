package iss.tim4.controller;

import iss.tim4.domain.Passenger;
import iss.tim4.service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
