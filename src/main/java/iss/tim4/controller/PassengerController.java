package iss.tim4.controller;

import iss.tim4.domain.dto.PassengerDTO;
import iss.tim4.domain.model.Passenger;
import iss.tim4.service.PassengerServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/passenger")
@AllArgsConstructor
public class PassengerController {

    @Autowired
    private PassengerServiceJPA passengerServiceJPA;

    // get all - /api/passenger
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getPassengers() {
        List<Passenger> passengers = passengerServiceJPA.findAll();


        // we have to convert passengers to passengers DTOs
        List<PassengerDTO> passengerDTOS = new ArrayList<>();
        for (Passenger p : passengers) {
            passengerDTOS.add(new PassengerDTO(p));
        }

        return new ResponseEntity<>(passengerDTOS, HttpStatus.OK);

    }


    // get by id - /api/passenger/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<PassengerDTO> getPassenger(@PathVariable("id") Integer id) {
        Passenger passenger = passengerServiceJPA.findOne(id);

        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PassengerDTO>(new PassengerDTO(passenger) , HttpStatus.OK);
    }


    // create /api/passenger
    @PostMapping(consumes = "application/json")
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) throws Exception {
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
        return new ResponseEntity<>(new PassengerDTO(passenger), HttpStatus.CREATED);
    }

    // update   --> /api/passenger/1
    // radi i ukoliko proslijedimo samo ona polja koja zelimo da promjenimo
    @PutMapping(value = "/{id}")
    public ResponseEntity<PassengerDTO> updateDriver(@RequestBody PassengerDTO passengerDTO, @PathVariable Integer id)
            throws Exception {
        Passenger passengerForUpdate = passengerServiceJPA.findOne(id);
        if (passengerForUpdate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // passengerDTO -> the one sent by the request
        // passengerForUpdate -> the one we are updating
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
        passengerForUpdate = passengerServiceJPA.save(passengerForUpdate); // TODO:provjeri ovaj update

        if (passengerForUpdate== null) {
            return new ResponseEntity<PassengerDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<PassengerDTO>(new PassengerDTO(passengerForUpdate), HttpStatus.OK);
    }



    /*

    //TODO: /api/passenger{activationId}
    @PostMapping(value="{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> activatePassenger(@RequestBody PassengerDTO passenger) throws Exception {
        PassengerDTO savedPassenger = passengerService.create(passenger);
        return new ResponseEntity<PassengerDTO>(savedPassenger, HttpStatus.CREATED);
    }

    // get passengers rides -> /api/passenger/1/ride TODO
    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDTO> getPassengerRides(@PathVariable("id") Long id) {
        PassengerDTO passenger = passengerService.findOne(id);

        if (passenger == null) {
            return new ResponseEntity<PassengerDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PassengerDTO>(passenger , HttpStatus.OK);
    }



*/

}
