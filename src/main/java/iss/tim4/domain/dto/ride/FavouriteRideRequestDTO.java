package iss.tim4.domain.dto.ride;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteRideRequestDTO {
    private String favoriteName;
    private RouteDTO[] locations;
    private PassengerDTOResult[] passengers;            // passengers who have it as a favourite location
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;



}


//    @PostMapping(value = "/create-example", consumes = "application/json")
//    public ResponseEntity<RideDTORequest> createRideExample(@RequestBody RideDTORequest rideDTO) throws Exception {
//
//        Driver driver = driverServiceJPA.findAvailableDriver(rideDTO);
//        if (driver == null) {
//            // TODO: Vrati gresku (KT2)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        double totalCost = rideServiceJPA.calculateCost(rideDTO);
//        Set<Passenger> passengers = passengerServiceJPA.getPassengers(rideDTO.getPassengers());
//        Set<Route> routes = routeServiceJPA.getRoutes(rideDTO);
//        VehicleType vehicleType = vehicleTypeServiceJPA.findByVehicleName(rideDTO.getVehicleType());
//
//        Ride newRide = new Ride(rideDTO);
//        newRide.setDriver(driver);
//        newRide.setTotalCost(totalCost);
//        newRide.setPassengers(passengers);
//        newRide.setRoutes(routes);
//        newRide.setVehicleType(vehicleType);
//
//        rideServiceJPA.save(newRide);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
