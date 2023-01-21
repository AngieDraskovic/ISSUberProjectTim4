package iss.tim4.controller;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.favourite.route.FavouriteRouteDTORequest;
import iss.tim4.domain.dto.favourite.route.FavouriteRouteDTOResult;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import iss.tim4.domain.dto.ride.RideDTO;
import iss.tim4.domain.dto.ride.RideDTOExample;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.model.*;
import iss.tim4.errors.UberException;
import iss.tim4.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private FavouriteRouteServiceJPA favouriteRouteServiceJPA;

    @Autowired
    private UserServiceJPA userServiceJPA;

    // get by id - /api/ride/1
    @GetMapping(value = "/{id}")
    public <T> ResponseEntity<T> getRide(@PathVariable("id") Integer id) {
        Ride ride = rideServiceJPA.findOne(id);
        if (ride == null) {
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist",HttpStatus.NOT_FOUND);
        }
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(new RideDTOResponse(ride) , HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('DRIVER', 'PASSENGER')")
    public ResponseEntity<RideDTOResponse> createRide(@Valid @RequestBody RideDTORequest rideDTO) throws Exception {
        if (!passengerServiceJPA.possibleOrder(rideDTO)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Driver driver = driverServiceJPA.findAvailableDriver(rideDTO);
        if (driver == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        Driver driver = driverServiceJPA.findOne(6);
        double totalCost = rideServiceJPA.calculateCost(rideDTO);
        Set<Passenger> passengers = passengerServiceJPA.getPassengers(rideDTO.getPassengers());
        Set<Route> routes = routeServiceJPA.getRoutes(rideDTO);
        VehicleType vehicleType = vehicleTypeServiceJPA.findByVehicleName(rideDTO.getVehicleType());

        Ride newRide = new Ride(rideDTO);
        newRide.setDriver(driver);
        newRide.setTotalCost(totalCost);
        newRide.setPassengers(passengers);
        newRide.setBabyTransport(rideDTO.getBabyTransport());
        newRide.setPetTransport(rideDTO.getPetTransport());

        newRide.setRoutes(routes);
        newRide.setVehicleType(vehicleType);
        newRide.setStatus(RideStatus.PENDING);

        rideServiceJPA.save(newRide);


        return new ResponseEntity<>(new RideDTOResponse(newRide), HttpStatus.OK);   // trebalo bi ovdje created
    }
    class CustomResponseEntity<T> extends ResponseEntity<T> {
        public CustomResponseEntity(T body, HttpStatus status) {
            super(body, status);
        }
    }
    @GetMapping(value="/passenger/{passengerId}/rideHistory")
    public ResponseEntity getPassengerRideHistory(@PathVariable("passengerId") Integer passengerId){
        Passenger passenger = passengerServiceJPA.findOne(passengerId);
        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Ride> rides = passenger.getRides();
        List<RideDTOResponse> rideDTOResponses = new ArrayList<>();
        for(Ride r : rides) {
            RideDTOResponse response = new RideDTOResponse(r);
            rideDTOResponses.add(response);
        }
        //?
        return new ResponseEntity<>(rideDTOResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/active")
    @PreAuthorize("hasAnyRole('PASSENGER', 'ADMIN')")
    public <T> ResponseEntity<T> getPassengerActiveRide(@PathVariable("passengerId") Integer passengerId) {
        Passenger passenger = passengerServiceJPA.findOne(passengerId);
        if (passenger == null) {
            return (ResponseEntity<T>) new ResponseEntity<String>("Active ride does not exist", HttpStatus.NOT_FOUND);
        }
        Set<Ride> rides = passenger.getRides();
        Ride activeRide = rides.iterator().next();      // ovdje samo zelim da uzmem prvi element liste da vrati, jer sad necu implementirati logiku za aktinu voznju
        activeRide.setStatus(RideStatus.ACTIVE);            // ovdje ovo nije potrebno, samo za kontrolnu tacku sam ovako uradila
        RideDTOResponse result = new RideDTOResponse(activeRide);
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/driver/{driverId}/active")
    @PreAuthorize("hasAnyRole('DRIVER', 'ADMIN')")
    public <T> ResponseEntity<T> getDriverActiveRide(@PathVariable("driverId") Integer driverId) {
        Driver driver = driverServiceJPA.findOne(driverId);
        if (driver == null) {
            return (ResponseEntity<T>) new ResponseEntity<String>("Active ride does not exist", HttpStatus.NOT_FOUND);
        }
        Set<Ride> rides = driver.getRides();
        Ride activeRide = rides.iterator().next();          // i ovdje kao i u gornjoj metodi :D
        activeRide.setStatus(RideStatus.ACTIVE);
        RideDTOResponse result = new RideDTOResponse(activeRide);

        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<RideDTOResponse> cancelRide(@PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!ride.getStatus().equals(RideStatus.PENDING) || !ride.getStatus().equals(RideStatus.STARTED)){
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot cancel a ride that is not in status PENDING or STARTED! ");
        }
        ride.getRejection().setReason("Ride is cancelled by passenger");
        ride.setStatus(RideStatus.CANCELED);
        RideDTOResponse result = new RideDTOResponse(ride);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/panic")
    @PreAuthorize("hasAnyRole('DRIVER', 'PASSENGER')")
    public ResponseEntity<PanicDTO> panicRide(@RequestBody  ReasonDTO reasonDTO, @PathVariable Integer id){
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Panic p = new Panic();
        //p.setId(563);
        p.setReason(reasonDTO.getReason());
        p.setTime(LocalDateTime.parse("2022-10-10T10:32:32"));
        p.setUser(userServiceJPA.getUserById(id));
        p.setRide(ride);
        PanicDTO result = new PanicDTO(p);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/accept")
    @PreAuthorize("hasRole('DRIVER')")
    public <T> ResponseEntity<T> acceptRide(@PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist!" , HttpStatus.NOT_FOUND);
        }
        if(!ride.getStatus().equals(RideStatus.PENDING)){
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot accept a ride that is not in status PENDING! ");
        }
        ride.setStatus(RideStatus.ACCEPTED);
        RideDTOResponse result = new RideDTOResponse(ride);
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);

    }
    @PutMapping(value = "/{id}/start")
    @PreAuthorize("hasRole('DRIVER')")
    public <T> ResponseEntity<T> startRide(@PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist!" , HttpStatus.NOT_FOUND);
        }
        if(!ride.getStatus().equals(RideStatus.ACCEPTED)){
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot start a ride that is not in status ACCEPTED! ");
        }
        ride.setStatus(RideStatus.STARTED);
        RideDTOResponse result = new RideDTOResponse(ride);
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/end")
    @PreAuthorize("hasRole('DRIVER')")
    public <T> ResponseEntity<T> finishRide(@PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist!" , HttpStatus.NOT_FOUND);
        }
        if(!ride.getStatus().equals(RideStatus.STARTED)){
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot end a ride that is not in status STARTED! ");
        }
        ride.setStatus(RideStatus.FINISHED);
        RideDTOResponse result = new RideDTOResponse(ride);
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/cancel")
    @PreAuthorize("hasRole('DRIVER')")
    public <T> ResponseEntity<T> rejectRide(@RequestBody ReasonDTO reasonDTO, @PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist!" , HttpStatus.NOT_FOUND);
        }
        if(!ride.getStatus().equals(RideStatus.PENDING) || !ride.getStatus().equals(RideStatus.ACCEPTED)){
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot cancel a ride that is not in status PENDING or ACCEPTED! ");
        }
        ride.setStatus(RideStatus.CANCELED);
        ride.getRejection().setReason(reasonDTO.getReason());
        RideDTOResponse result = new RideDTOResponse(ride);
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);

    }


    @GetMapping(value = "/passenger/{id}")
    public ResponseEntity<Set<RideDTOResponse>> getPassengerRides(@PathVariable("id") Integer id) {
        List<Ride> rides = rideServiceJPA.findByPassengerId(id);
        Set<RideDTOResponse> ridesDTO = new HashSet<>();
        for (Ride ride : rides) {
            ridesDTO.add(new RideDTOResponse(ride));
        }
        return new ResponseEntity<>(ridesDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/favorites", consumes = "application/json")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<FavouriteRouteDTOResult> createFavouriteRoutes(@RequestBody FavouriteRouteDTORequest favouriteRouteDTORequest) throws Exception {

        FavouriteRoute favouriteRoute = new FavouriteRoute(favouriteRouteDTORequest);

        Set<Passenger> passengers = new HashSet<Passenger>();
        for (PassengerRideDTO passengerRideDTO : favouriteRouteDTORequest.getPassengers()) {
            passengers.add(passengerServiceJPA.findOne(passengerRideDTO.getId()));
        }
        Set<Route> locations = routeServiceJPA.getRoutes2(favouriteRouteDTORequest.getLocations());

        favouriteRoute.setPassengers(passengers);
        favouriteRoute.setLocations(locations);

        favouriteRouteServiceJPA.save(favouriteRoute);
        FavouriteRouteDTOResult favouriteRouteDTOResult = new FavouriteRouteDTOResult(favouriteRoute);

        return new ResponseEntity<>(favouriteRouteDTOResult, HttpStatus.OK);
    }

    @GetMapping(value = "/favorites")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<Set<FavouriteRouteDTOResult>> getFavoriteLocations() {

        Set<FavouriteRouteDTOResult> favouriteRouteDTOResults = new HashSet<FavouriteRouteDTOResult>();
        List<FavouriteRoute> favouriteRoutes = favouriteRouteServiceJPA.findAll();
        for (FavouriteRoute favouriteRoute : favouriteRoutes) {
            favouriteRouteDTOResults.add(new FavouriteRouteDTOResult(favouriteRoute));
        }

        return new ResponseEntity<>(favouriteRouteDTOResults, HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorites/{id}")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<String> deleteFavoriteRoute(@PathVariable("id") Integer id) {
        if(favouriteRouteServiceJPA.findOne(id) == null){
            return new ResponseEntity<String>("Favorite location does not exist!", HttpStatus.NOT_FOUND);
        }
        favouriteRouteServiceJPA.remove(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);     // TODO: Treba deleted, ali ne znam koji je status
    }

}
