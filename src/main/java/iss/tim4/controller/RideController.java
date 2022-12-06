package iss.tim4.controller;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.dto.*;
import iss.tim4.domain.model.*;
import iss.tim4.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/ride")
@AllArgsConstructor
public class RideController {

    @Autowired
    private RideServiceJPA rideServiceJPA;
    @Autowired
    private PassengerServiceJPA passengerServiceJPA;
    @Autowired
    private DriverServiceJPA driverServiceJPA;
    @Autowired
    private VehicleTypeServiceJPA vehicleTypeServiceJPA;

    @Autowired
    private RejectionServiceJPA rejectionServiceJPA;

    // get by id - /api/ride/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<RideDTOResponse> getRide(@PathVariable("id") Integer id) {
        Ride ride = rideServiceJPA.findOne(id);
        if (ride == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RideDTOResponse>(new RideDTOResponse(ride) , HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<RideDTOResponse> createRide(@RequestBody RideDTORequest rideDTO) throws Exception {
        Ride ride = new Ride();
        VehicleType v = vehicleTypeServiceJPA.findOne(1);
        v.setVehicleName(rideDTO.getVehicleType()); // jako zbunjujuca linija koda ali stvarno nisam ja kriva
        ride.setVehicleType(v);

        ride.setBabyTransport(rideDTO.getBabyTransport());
        ride.setPetTransport(rideDTO.getPetTransport());
        PassengerRideDTO[] passengerRideDTOS = rideDTO.getPassengers();

        // setujem passengere
        Set<Passenger> passengerSet = new HashSet<Passenger>();
        for(int i = 0; i < passengerRideDTOS.length; i++){
            passengerSet.add(passengerServiceJPA.findOne(passengerRideDTOS[0].getId()));

        }
        ride.setPassengers(passengerSet);

        ride.setDriver(driverServiceJPA.findOne(1));

        // setujem rute
        RouteDTO[] routesDTO = rideDTO.getLocations();
        Set<Route> routeSet = new HashSet<Route>();
        for(int i = 0; i < routesDTO.length; i++){
            Route r = new Route();
            Location l1 = new Location();
            Location l2 = new Location();
            l1.setGeoLength(routesDTO[i].getDeparture().getLatitude());
            l1.setGeoWidth(routesDTO[i].getDeparture().getLongitude());
            l1.setAddress(routesDTO[i].getDeparture().getAddress());
            l2.setGeoLength(routesDTO[i].getDestination().getLatitude());
            l2.setGeoWidth(routesDTO[i].getDestination().getLongitude());
            l2.setAddress(routesDTO[i].getDestination().getAddress());
            r.setStartLocation(l1);
            r.setEndLocation(l2);
            r.setKilometers(2.9);
            routeSet.add(r);
        }

        ride.setRoutes(routeSet);
        ride.setRejection(rejectionServiceJPA.findOne(1));
        ride.setTotalCost(400.2);
        ride.setEstimatedTimeInMinutes(10.0);
        ride.setStatus(RideStatus.PENDING);
        ride.setEndTime(LocalDateTime.parse("2022-10-10T10:30:30"));
        ride.setStartTime(LocalDateTime.parse("2022-11-11T11:31:31"));
        ride.setPanic(false);
        ride = rideServiceJPA.save(ride);
        return new ResponseEntity<>(new RideDTOResponse(ride), HttpStatus.CREATED);
    }
}
