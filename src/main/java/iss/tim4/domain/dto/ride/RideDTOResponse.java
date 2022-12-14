package iss.tim4.domain.dto.ride;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RejectionDTO;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.driver.DriverRideDTO;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Ride;
import iss.tim4.domain.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDTOResponse {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalCost;
    private DriverRideDTO driver;
    private Double estimatedTimeInMinutes;
    private RideStatus status;
    private RejectionDTO rejection;
    private Boolean babyTransport;
    private Boolean petTransport;
    private VehicleName vehicleType;
    private PassengerRideDTO[] passengers;
    private RouteDTO[] locations;

    public RideDTOResponse(Ride ride) {
        this.id = ride.getId();
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.totalCost = ride.getTotalCost();
        this.driver = new DriverRideDTO(ride.getDriver());
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
        this.status = ride.getStatus();
        this.rejection = new RejectionDTO(ride.getRejection());
        Set<Route> routes = ride.getRoutes();
        RouteDTO[] locationDTOS = new RouteDTO[routes.size()];
        iter = 0;
        for(Route route : routes){
            locationDTOS[iter] = new RouteDTO(route);
            iter++;
        }
        this.locations = locationDTOS;

    }
}
