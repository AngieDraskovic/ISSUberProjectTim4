package iss.tim4.controller;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import iss.tim4.domain.dto.ride.RideDTO;
import iss.tim4.domain.dto.ride.RideDTOExample;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.model.*;
import iss.tim4.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/ride")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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
    private RouteServiceJPA routeServiceJPA;
    @Autowired
    private RejectionServiceJPA rejectionServiceJPA;

    @Autowired
    private UserServiceJPA userServiceJPA;

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
        return new ResponseEntity<>(new RideDTOResponse(ride), HttpStatus.OK);   // trebalo bi ovdje created
    }


    @GetMapping(value = "/passenger/{passengerId}/active")
    public ResponseEntity<RideDTOResponse> getPassengerActiveRide(@PathVariable("passengerId") Integer passengerId) {
        Passenger passenger = passengerServiceJPA.findOne(passengerId);
        Set<Ride> rides = passenger.getRides();
        Ride activeRide = rides.iterator().next();      // ovdje samo zelim da uzmem prvi element liste da vrati, jer sad necu implementirati logiku za aktinu voznju
        activeRide.setStatus(RideStatus.ACTIVE);
        RideDTOResponse result = new RideDTOResponse(activeRide);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/driver/{driverId}/active")
    public ResponseEntity<RideDTOResponse> getDriverActiveRide(@PathVariable("driverId") Integer driverId) {
        Driver driver = driverServiceJPA.findOne(driverId);
        Set<Ride> rides = driver.getRides();
        Ride activeRide = rides.iterator().next();          // i ovdje kao i u gornjoj metodi :D
        activeRide.setStatus(RideStatus.ACTIVE);
        RideDTOResponse result = new RideDTOResponse(activeRide);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw")
    public ResponseEntity<RideDTOResponse> cancelRide(@PathVariable Integer id){
        Ride ride = rideServiceJPA.findOne(id);
        ride.getRejection().setReason("Ride is cancelled by passenger");
        ride.setStatus(RideStatus.CANCELED);
        RideDTOResponse result = new RideDTOResponse(ride);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/panic")
    public ResponseEntity<PanicDTO> panicRide(@RequestBody  ReasonDTO reasonDTO, @PathVariable Integer id){
        Ride ride = rideServiceJPA.findOne(id);
        Panic p = new Panic();
        p.setId(563);
        p.setReason(reasonDTO.getReason());
        p.setTime(LocalDateTime.parse("2022-10-10T10:32:32"));
        p.setUser(userServiceJPA.findOne(1));
        p.setRide(ride);
        PanicDTO result = new PanicDTO(p);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/accept")
    public ResponseEntity<RideDTOResponse> acceptRide(@PathVariable Integer id){
        Ride ride = rideServiceJPA.findOne(id);
        ride.setStatus(RideStatus.ACCEPTED);
        RideDTOResponse result = new RideDTOResponse(ride);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/end")
    public ResponseEntity<RideDTOResponse> finishRide(@PathVariable Integer id){
        Ride ride = rideServiceJPA.findOne(id);
        ride.setStatus(RideStatus.FINISHED);
        RideDTOResponse result = new RideDTOResponse(ride);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<RideDTOResponse> rejectRide(@RequestBody ReasonDTO reasonDTO, @PathVariable Integer id){
        Ride ride = rideServiceJPA.findOne(id);
        ride.setStatus(RideStatus.REJECTED);
        ride.getRejection().setReason(reasonDTO.getReason());
        RideDTOResponse result = new RideDTOResponse(ride);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/create-example", consumes = "application/json")
    public ResponseEntity<RideDTOExample> createRideExample(@RequestBody RideDTOExample rideDTO) throws Exception {

        Driver driver = driverServiceJPA.findAvailableDriver(rideDTO);
        if (driver == null) {
            // TODO: Vrati gresku (KT2)
        }

        double totalCost = rideServiceJPA.calculateCost(rideDTO);
        Set<Passenger> passengers = passengerServiceJPA.getPassengers(rideDTO.getPassengers());
        Set<Route> routes = routeServiceJPA.getRoutes(rideDTO);
        VehicleType vehicleType = vehicleTypeServiceJPA.findByVehicleName(VehicleName.valueOf(rideDTO.getVehicleName()));

        Ride newRide = new Ride(rideDTO);
        newRide.setDriver(driver);
        newRide.setTotalCost(totalCost);
        newRide.setPassengers(passengers);
        newRide.setRoutes(routes);
        newRide.setVehicleType(vehicleType);

        rideServiceJPA.save(newRide);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{id}")
    public ResponseEntity<Set<RideDTOResponse>> getPassengerRides(@PathVariable("id") Integer id) {
        List<Ride> rides = rideServiceJPA.findByPassengerId(id);
        Set<RideDTOResponse> ridesDTO = new HashSet<>();
        for (Ride ride : rides) {
            ridesDTO.add(new RideDTOResponse(ride));
        }
        return new ResponseEntity<>(ridesDTO, HttpStatus.OK);

//        Ride ride = rideServiceJPA.findOne(id);
//        if (ride == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<RideDTOResponse>(new RideDTOResponse(ride) , HttpStatus.OK);
    }

}
