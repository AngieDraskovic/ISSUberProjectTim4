package iss.tim4.domain.dto;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.driver.DriverRideDTO;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Ride;
import iss.tim4.domain.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneRideOfPassengerDTO {
    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalCost;
    private DriverRideDTO driver;
    private RideStatus status;
    private Double estimatedTimeInMinutes;
    private Boolean babyTransport;
    private Boolean petTransport;
    private VehicleName vehicleType;
    private PassengerRideDTO[] passengers;
    private RouteDTO[] locations;
    private RejectionDTO rejection;

    public OneRideOfPassengerDTO(Ride ride){
        this.id = ride.getId();
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.totalCost = ride.getTotalCost();
        this.driver = new DriverRideDTO(ride.getDriver());
        this.status = ride.getStatus();
        Set<Passenger> passengers = ride.getPassengers();
        PassengerRideDTO[] p = new PassengerRideDTO[passengers.size()];
        int iter = 0;
        for(Passenger passenger : passengers){
            p[iter] = new PassengerRideDTO(passenger);
            iter++;
        }
        this.passengers = p;
        this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
        this.vehicleType = ride.getVehicleType().getVehicleName();
        this.babyTransport = ride.getBabyTransport();
        this.petTransport = ride.getPetTransport();
        Set<Route> routes = ride.getRoutes();
        RouteDTO[] locationDTOS = new RouteDTO[routes.size()];
        iter = 0;
        for(Route route : routes){
            locationDTOS[iter] = new RouteDTO(route);
            iter++;
        }
        this.locations = locationDTOS;
        this.rejection = new RejectionDTO(ride.getRejection());

    }
}
