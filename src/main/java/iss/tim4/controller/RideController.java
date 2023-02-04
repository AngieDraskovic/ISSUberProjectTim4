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
import iss.tim4.domain.dto.working.hours.WorkingHoursDTORequest;
import iss.tim4.domain.dto.working.hours.WorkingHoursDTOResponse;
import iss.tim4.domain.model.*;
import iss.tim4.errors.UberException;
import iss.tim4.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private FavouriteRouteServiceJPA favouriteRouteServiceJPA;
    @Autowired
    private DriverSurveyController driverSurveyController;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private VehicleServiceJPA vehicleServiceJPA;
    @Autowired
    private UserServiceJPA userServiceJPA;

    @Autowired
    private LocationServiceJPA locationServiceJPA;
    @Autowired
    private WorkingHoursServiceJPA workingHoursServiceJPA;


    // get by id - /api/ride/1
    @GetMapping(value = "/{id}")
    public <T> ResponseEntity<T> getRide(@PathVariable("id") Integer id) {
        Ride ride = rideServiceJPA.findOne(id);
        if (ride == null) {
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist", HttpStatus.NOT_FOUND);
        }
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(new RideDTOResponse(ride), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<RideDTOResponse> createRide(@Valid @RequestBody RideDTORequest rideDTO, Principal user) throws Exception {
//        rideDTO.getLocations()[0].getDeparture()
        rideDTO.setAgreementCode(user.hashCode());
        var actualUser = userServiceJPA.getUser(user.getName());
        if (Arrays.stream(rideDTO.getPassengers()).noneMatch(p -> Objects.equals(p.getId(), actualUser.getId()))) {
            throw new UberException(HttpStatus.BAD_REQUEST, "User should be passenger");
        }

        if (!passengerServiceJPA.possibleOrder(rideDTO)) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot order a ride with these passengers!");
        }

        List<Driver> alreadyChecked = new ArrayList<>();
        Driver driver = null;
        messagingTemplate.convertAndSend(
                "/topic/search-status/" + actualUser.getId(),
                new GenericMessage<>("Asking driver...")
        );

        while (true) {
            var closestDriver = driverServiceJPA.findAvailableDriver(rideDTO, alreadyChecked);
            if (closestDriver == null) {
                messagingTemplate.convertAndSend(
                        "/topic/search-status/" + actualUser.getId(),
                        new GenericMessage<>("Sorry, no drivers free at the moment. Try in a few minutes...")
                );
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            messagingTemplate.convertAndSend(
                    "/topic/driver-survey/" + closestDriver.getId(),
                    new GenericMessage<>(rideDTO)
            );
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            if (driverSurveyController.driverRideAgreement.containsKey(closestDriver.getId())
                    && Objects.equals(driverSurveyController.driverRideAgreement.get(closestDriver.getId()),
                    rideDTO.getAgreementCode())) {
                messagingTemplate.convertAndSend(
                        "/topic/search-status/" + actualUser.getId(),
                        new GenericMessage<>("Driver found!")
                );
                driver = closestDriver;
                break;
            } else {
                DecimalFormat df = new DecimalFormat("0.0");
                String distance = df.format(closestDriver.getVehicle().getCurrLocation().distanceTo(rideDTO.getLocations()[0].getDeparture()));
                messagingTemplate.convertAndSend(
                        "/topic/search-status/" + actualUser.getId(),
                        new GenericMessage<>("Increasing radius... (" + distance + " KM)")
                );
                alreadyChecked.add(closestDriver);
            }

        }

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

    @GetMapping(value = "/passenger/{passengerId}/rideHistory")
    public ResponseEntity getPassengerRideHistory(@PathVariable("passengerId") Integer passengerId) {
        Passenger passenger = passengerServiceJPA.findOne(passengerId);
        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Ride> rides = passenger.getRides();
        List<RideDTOResponse> rideDTOResponses = new ArrayList<>();
        for (Ride r : rides) {
            RideDTOResponse response = new RideDTOResponse(r);
            rideDTOResponses.add(response);
        }
        return new ResponseEntity<>(rideDTOResponses, HttpStatus.OK);
    }

    @GetMapping(value="/driver/{driverId}/rideHistory")
    public ResponseEntity getDriverRideHistory(@PathVariable("driverId") Integer driverId){
        Driver driver = driverServiceJPA.findOne(driverId);
        if (driver == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Ride> rides = driver.getRides();
        List<RideDTOResponse> rideDTOResponses = new ArrayList<>();
        for(Ride r : rides) {
            RideDTOResponse response = new RideDTOResponse(r);
            rideDTOResponses.add(response);
        }
        //?
        return new ResponseEntity<>(rideDTOResponses, HttpStatus.OK);
    }

    @GetMapping("driver/{driverId}/{startDate}/{endDate}")
    public ResponseEntity<Object> getRidesDriverByDate(@PathVariable Integer driverId,
                                           @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                           @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Driver driver = driverServiceJPA.findOne(driverId);

        if (driver == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Ride> rides = driver.getRides();
        LocalDateTime s = startDate.atStartOfDay();
        LocalDateTime e = endDate.atStartOfDay();
        List<Ride> filteredRides = rideServiceJPA.filterRidesByDate(s, e, rides);
        List<RideDTOResponse> rideDTOResponses = new ArrayList<>();
        for(Ride r : filteredRides) {
            RideDTOResponse response = new RideDTOResponse(r);
            rideDTOResponses.add(response);
        }

        return new ResponseEntity<>(rideDTOResponses, HttpStatus.OK);
    }

    @GetMapping("passenger/{passengerId}/{startDate}/{endDate}")
    public ResponseEntity<Object> getRidesPassengerByDate(@PathVariable Integer passengerId,
                                                       @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                       @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        Passenger passenger = passengerServiceJPA.findOne(passengerId);

        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Ride> rides = passenger.getRides();
        LocalDateTime s = startDate.atStartOfDay();
        LocalDateTime e = endDate.atStartOfDay();
        List<Ride> filteredRides = rideServiceJPA.filterRidesByDate(s, e, rides);
        List<RideDTOResponse> rideDTOResponses = new ArrayList<>();
        for(Ride r : filteredRides) {
            RideDTOResponse response = new RideDTOResponse(r);
            rideDTOResponses.add(response);
        }

        return new ResponseEntity<>(rideDTOResponses, HttpStatus.OK);
    }

    @GetMapping("{startDate}/{endDate}")
    public ResponseEntity<Object> getRidesPassengerByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<Ride> rides = rideServiceJPA.findAll();
        LocalDateTime s = startDate.atStartOfDay();
        LocalDateTime e = endDate.atStartOfDay();
        List<Ride> filteredRides = rideServiceJPA.filterListOfRidesByDate(s, e, rides);
        List<RideDTOResponse> rideDTOResponses = new ArrayList<>();
        for(Ride r : filteredRides) {
            RideDTOResponse response = new RideDTOResponse(r);
            rideDTOResponses.add(response);
        }

        return new ResponseEntity<>(rideDTOResponses, HttpStatus.OK);
    }

    @GetMapping(value="/all")
    public ResponseEntity getAllRides(){
        List<Ride> rides = rideServiceJPA.findAll();
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

        Ride activeRide = new Ride();
        Set<Ride> rides = passenger.getRides();
        for (Ride ride: rides)
            if (ride.getStatus().equals(RideStatus.ACTIVE)) {
                activeRide = ride;
                break;
            }

        RideDTOResponse result;
        if (activeRide.getDriver() != null)
            result = new RideDTOResponse(activeRide);
        else
            result = new RideDTOResponse();
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/driver/{driverId}/active")
    @PreAuthorize("hasAnyRole('DRIVER', 'ADMIN')")
    public <T> ResponseEntity<T> getDriverActiveRide(@PathVariable("driverId") Integer driverId) {
        Driver driver = driverServiceJPA.findOne(driverId);
        if (driver == null) {
            return (ResponseEntity<T>) new ResponseEntity<String>("Active ride does not exist", HttpStatus.NOT_FOUND);
        }
        Ride activeRide = new Ride();
        Set<Ride> rides = driver.getRides();
        for (Ride ride: rides)
            if (ride.getStatus().equals(RideStatus.ACTIVE)) {
                activeRide = ride;
                break;
            }

        RideDTOResponse result;
        if (activeRide.getDriver() != null)
            result = new RideDTOResponse(activeRide);
        else
            result = new RideDTOResponse();

        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<RideDTOResponse> cancelRide(@PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if (ride == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println(ride.getStatus());
        if (!ride.getStatus().equals(RideStatus.ACCEPTED) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot cancel a ride that is not in status PENDING or STARTED! ");
        }
       // ride.getRejection().setReason("Ride is cancelled by passenger");
        ride.setStatus(RideStatus.CANCELED);

        RideDTOResponse result = new RideDTOResponse(ride);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/accept")
    @PreAuthorize("hasRole('DRIVER')")
    public <T> ResponseEntity<T> acceptRide(@PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if (ride == null) {
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        if (!ride.getStatus().equals(RideStatus.PENDING)) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot accept a ride that is not in status PENDING! ");
        }
        ride.setStatus(RideStatus.ACCEPTED);
        rideServiceJPA.save(ride);

        RideDTOResponse result = new RideDTOResponse(ride);
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/start")
    @PreAuthorize("hasRole('DRIVER')")
    public <T> ResponseEntity<T> startRide(@PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if (ride == null) {
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        if(!ride.getStatus().equals(RideStatus.ACCEPTED) && !ride.getStatus().equals(RideStatus.PENDING)){
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot start a ride that is not in status ACCEPTED! ");
        }
        ride.setStatus(RideStatus.ACTIVE);
        ride.setStartTime(LocalDateTime.now());
        rideServiceJPA.save(ride);

        Driver driver = ride.getDriver();
        driver.setActive(false);
        driverServiceJPA.save(driver);

        Vehicle vehicle = driver.getVehicle();
        vehicle.setAvailable(false);
        vehicleServiceJPA.save(vehicle);

        RideDTOResponse result = new RideDTOResponse(ride);
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/end")
    @PreAuthorize("hasRole('DRIVER')")
    public <T> ResponseEntity<T> finishRide(@PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if (ride == null) {
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        if (!ride.getStatus().equals(RideStatus.ACTIVE)) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot end a ride that is not in status ACTIVE! ");
        }
        ride.setStatus(RideStatus.FINISHED);
        ride.setEndTime(LocalDateTime.now());
        rideServiceJPA.save(ride);

        Vehicle vehicle = ride.getDriver().getVehicle();
        vehicle.setAvailable(true);
        vehicleServiceJPA.save(vehicle);

        RideDTOResponse result = new RideDTOResponse(ride);
        return (ResponseEntity<T>) new ResponseEntity<RideDTOResponse>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/cancel")
    @PreAuthorize("hasRole('DRIVER')")
    public <T> ResponseEntity<T> rejectRide(@RequestBody RejectionDTO rejectionDTO, @PathVariable Integer id) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return (ResponseEntity<T>) new ResponseEntity<String>("Ride does not exist!" , HttpStatus.NOT_FOUND);
        }
        System.out.println(ride.getStatus());
        if(!ride.getStatus().equals(RideStatus.PENDING) && !ride.getStatus().equals(RideStatus.ACCEPTED)){
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot cancel a ride that is not in status PENDING or ACCEPTED! ");
        }
        ride.setStatus(RideStatus.CANCELED);

        Rejection rejection = new Rejection(rejectionDTO);
        rejection.setRide(ride);
        rejection.setUser(ride.getDriver());
        ride.setRejection(rejection);
        rideServiceJPA.save(ride);

        RideDTOResponse result = new RideDTOResponse(ride);
        return (ResponseEntity<T>) new ResponseEntity<>(result, HttpStatus.OK);

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
    public ResponseEntity<Set<FavouriteRouteDTOResult>> getFavoriteRoutes() {

        Set<FavouriteRouteDTOResult> favouriteRouteDTOResults = new HashSet<FavouriteRouteDTOResult>();
        List<FavouriteRoute> favouriteRoutes = favouriteRouteServiceJPA.findAll();
        for (FavouriteRoute favouriteRoute : favouriteRoutes) {
            favouriteRouteDTOResults.add(new FavouriteRouteDTOResult(favouriteRoute));
        }

        return new ResponseEntity<>(favouriteRouteDTOResults, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}/favorites")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<List<FavouriteRouteDTOResult>> getFavoriteRoutesByPassenger(@PathVariable("id") Integer id) {
        Passenger passenger = passengerServiceJPA.findOne(id);
        List<FavouriteRouteDTOResult> favouriteRouteDTOResults = new ArrayList<FavouriteRouteDTOResult>();

        for (FavouriteRoute favouriteRoute : passenger.getFavouriteRoutes()) {
            favouriteRouteDTOResults.add(new FavouriteRouteDTOResult(favouriteRoute));
        }

        return new ResponseEntity<>(favouriteRouteDTOResults, HttpStatus.OK);
    }


    @DeleteMapping(value = "/favorites/{id}/{passengerId}")
    @PreAuthorize("hasRole('PASSENGER')")
    public ResponseEntity<String> deleteFavoriteRoute(@PathVariable("id") Integer id, @PathVariable("passengerId") Integer passengerId) throws Exception {
        if(rideServiceJPA.findOne(id) == null){
            return new ResponseEntity<String>("Favorite location does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideServiceJPA.findOne(id);
        Route route = ride.getRoutes().stream().findFirst().orElseGet(null);
        String departureAddress = route.getStartLocation().getAddress();
        String destinationAddress = route.getEndLocation().getAddress();

//        Passenger passenger = passengerServiceJPA.findOne(passengerId);

        Passenger passenger = passengerServiceJPA.findOne(passengerId);
        FavouriteRoute favouriteRoute = passengerServiceJPA.findFavouriteRouteByAddress(passenger, departureAddress, destinationAddress);
        if (favouriteRoute == null) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Cannot delete route that does not exist!");
        }
        passenger.getFavouriteRoutes().remove(favouriteRoute);
        passengerServiceJPA.save(passenger);

//        Route route = ride.getRoutes().stream().findFirst().orElseGet(null);
//        String departureAddress = route.getStartLocation().getAddress();
//        String destinationAddress = route.getEndLocation().getAddress();
//        favouriteRouteServiceJPA.removeRouteFromFavourites(passengerId, departureAddress, destinationAddress);
//
//        passenger.removeFromFavorites(departureAddress, destinationAddress);
//        passengerServiceJPA.save(passenger);

//        favouriteRouteServiceJPA.remove(id);
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);     // TODO: Treba deleted, ali ne znam koji je status
    }

    @PutMapping(value = "/{id}/panic-ride")
    @PreAuthorize("hasAnyRole('DRIVER', 'PASSENGER')")
    public ResponseEntity<PanicDTO> panicRide2(@RequestBody  PanicDTORequest panicDTORequest, @PathVariable Integer id, Principal user) throws UberException {
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(ride.getPanic()!=null){
            throw new UberException(HttpStatus.BAD_REQUEST , "There is already a panic for this ride caused by: " + ride.getPanic().getUser().getEmail());
        }
        // user that clicked on panic
        var actualUser = userServiceJPA.getUser(user.getName());
        Panic p = new Panic(panicDTORequest);
        p.setUser(actualUser);
        p.setRide(ride);
        p.setTime(LocalDateTime.now());
        ride.setPanic(p);
        rideServiceJPA.save(ride);
        PanicDTO result = new PanicDTO(p);
        List<User> admins = userServiceJPA.findByRole(Role.ADMIN);
        // sends message to all admins
        for(User admin : admins){
            messagingTemplate.convertAndSend(
                    "/topic/panic/" + admin.getId(),
                    new GenericMessage<>(result)
            );
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @GetMapping(value="/active")
    public ResponseEntity activeRides(){
        List<Ride> activeRides = rideServiceJPA.getActiveRides();
        List<RideDTO> response = new ArrayList<>();
        for(Ride r : activeRides){
            response.add(new RideDTO(r));
        }

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
