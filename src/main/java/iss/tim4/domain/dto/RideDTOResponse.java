package iss.tim4.domain.dto;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
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

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalCost;
    private DriverRideDTO driver;
    private Double estimatedTimeInMinutes;
    private RideStatus status;
    private RejectionDTO rejectionDTO;
    private Boolean babyTransport;
    private Boolean petTransport;
    private VehicleName vehicleType;
    private PassengerRideDTO[] passengers;
    private LocationDTO[] locations;

    public RideDTOResponse(Ride ride) {
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
        this.rejectionDTO = new RejectionDTO(ride.getRejection());
        Set<Route> routes = ride.getRoutes();
        LocationDTO[] locationDTOS = new LocationDTO[routes.size()*2];

        // imacu niz lokacija iz sv
        iter = 0;
        for(Route route : routes){
            locationDTOS[iter] = new LocationDTO(route.getStartLocation());
            locationDTOS[iter+1] = new LocationDTO(route.getEndLocation());
            iter++;
        }
        this.locations = locationDTOS;

    }
}
