package iss.tim4.controller;

import iss.tim4.domain.dto.LocationDTO;
import iss.tim4.domain.dto.WorkingHoursDTOResponse;
import iss.tim4.domain.dto.WorkingHoursDTOResult;
import iss.tim4.domain.model.Location;
import iss.tim4.domain.model.Vehicle;
import iss.tim4.domain.model.WorkingHours;
import iss.tim4.service.LocationServiceJPA;
import iss.tim4.service.VehicleServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle")
@AllArgsConstructor
public class VehicleController {

    @Autowired
    private VehicleServiceJPA vehicleServiceJPA;

    @Autowired
    private LocationServiceJPA locationServiceJPA;


    // #1 update vehicle location - PUT api/vehicle/1/location
    @PutMapping(value = "/{id}/location", consumes = "application/json")
    public ResponseEntity<String> updateWorkingHour(@RequestBody LocationDTO locationDTO, @PathVariable("id") Integer id) {
        Vehicle vehicle = vehicleServiceJPA.findOne(Long.valueOf(id));
        Location location = vehicle.getCurrLocation();
        location.update(locationDTO);
        locationServiceJPA.save(location);
        return new ResponseEntity<String>("Coordinates successfully updated", HttpStatus.OK);
    }

}
