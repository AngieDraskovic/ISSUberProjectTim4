package iss.tim4.controller;

import iss.tim4.domain.dto.LocationDTO;
import iss.tim4.domain.dto.VehicleDTOResponse;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.model.Location;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Vehicle;
import iss.tim4.service.LocationServiceJPA;
import iss.tim4.service.VehicleServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class VehicleController {

    @Autowired
    private VehicleServiceJPA vehicleServiceJPA;

    @Autowired
    private LocationServiceJPA locationServiceJPA;

    // #1
     @GetMapping("/all")
    public ResponseEntity getAllVehicles() {
         vehicleServiceJPA.checkVehicleAvailability();
         List<Vehicle> vehicles = vehicleServiceJPA.findAll();
         List<VehicleDTOResponse> vehicleDTOResponses = new ArrayList<>();
         for(Vehicle v : vehicles) {
            VehicleDTOResponse result = new VehicleDTOResponse(v);
            vehicleDTOResponses.add(result);
         }
         return new ResponseEntity<>(vehicleDTOResponses, HttpStatus.OK);

    }
    // #2 update vehicle location - PUT api/vehicle/1/location
    @PutMapping(value = "/{id}/location", consumes = "application/json")
    public ResponseEntity<String> updateWorkingHour(@RequestBody LocationDTO locationDTO, @PathVariable("id") Integer id) {
        Vehicle vehicle = vehicleServiceJPA.findOne(id);
        Location location = vehicle.getCurrLocation();
        location.update(locationDTO);
        locationServiceJPA.save(location);
        return new ResponseEntity<String>("Coordinates successfully updated", HttpStatus.NO_CONTENT);
        // 400 -> bad request, 404 -> not found, 403 -> forbidden
    }



}
