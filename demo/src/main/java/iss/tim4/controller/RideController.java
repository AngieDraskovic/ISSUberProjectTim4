package iss.tim4.controller;

import iss.tim4.domain.dto.PassengerDTO;
import iss.tim4.domain.dto.RideDTO;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Ride;
import iss.tim4.service.RideService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ride")
@AllArgsConstructor
public class RideController {

    @Autowired
    private RideService rideService;

    @GetMapping
    public ResponseEntity<List<RideDTO>> getPassengers() {
        List<Ride> rides = rideService.findAll();

        List<RideDTO> rideDTOS = new ArrayList<>();
        for (Ride r : rides) {
            rideDTOS.add(new RideDTO(r));
        }

        return new ResponseEntity<>(rideDTOS, HttpStatus.OK);

    }
}
