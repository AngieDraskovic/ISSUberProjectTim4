package iss.tim4.controller;

import iss.tim4.domain.dto.LocationDTO;
import iss.tim4.domain.dto.VehicleDTOResponse;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.model.Location;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Vehicle;
import iss.tim4.errors.UberException;
import iss.tim4.service.LocationServiceJPA;
import iss.tim4.service.VehicleServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
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

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    // #1
    @Transactional
    @Scheduled(cron = "*/5 * * * * *")
    public void getAllVehicles() {
        vehicleServiceJPA.checkVehicleAvailability();
        List<Vehicle> vehicles = vehicleServiceJPA.findAll();
        List<VehicleDTOResponse> vehicleDTOResponses = new ArrayList<>();
        for (Vehicle v : vehicles) {
            VehicleDTOResponse result = new VehicleDTOResponse(v);
            System.out.println(result.getCurrentLocation().getLatitude() + " " + result.getCurrentLocation().getLongitude());
            vehicleDTOResponses.add(result);
        }
        messagingTemplate.convertAndSend("/topic/vehicles", new GenericMessage<>(vehicleDTOResponses));
    }


    // #2 update vehicle location - PUT api/vehicle/1/location
    @PreAuthorize("hasRole('DRIVER')")
    @PutMapping(value = "/{id}/location", consumes = "application/json")
    public ResponseEntity<String> updateWorkingHour(@Valid @RequestBody LocationDTO locationDTO, @PathVariable("id") Integer id) throws UberException {
        Vehicle vehicle = vehicleServiceJPA.findOne(id);
        if (vehicle == null) {
            return new ResponseEntity<String>("Vehicle does not exist!", HttpStatus.NOT_FOUND);
        }
        Location location = vehicle.getCurrLocation();
        location.update(locationDTO);
        locationServiceJPA.save(location);
        return new ResponseEntity<String>("Coordinates successfully updated", HttpStatus.NO_CONTENT);
        // 400 -> bad request, 404 -> not found, 403 -> forbidden
    }



}
