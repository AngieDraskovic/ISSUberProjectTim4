package iss.tim4.controller;

import iss.tim4.domain.dto.PassengerDTORequest;
import iss.tim4.domain.dto.PassengerDTOResult;
import iss.tim4.domain.dto.PassengerRideDTO;
import iss.tim4.domain.dto.RideDTOResponse;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Ride;
import iss.tim4.service.PassengerServiceJPA;
import iss.tim4.service.RideServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/ride")
@AllArgsConstructor
public class RideController {

    @Autowired
    private RideServiceJPA rideServiceJPA;

    // get by id - /api/ride/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<RideDTOResponse> getPassenger(@PathVariable("id") Integer id) {
        Ride ride = rideServiceJPA.findOne(id);
        if (ride == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RideDTOResponse>(new RideDTOResponse(ride) , HttpStatus.OK);
    }
}
