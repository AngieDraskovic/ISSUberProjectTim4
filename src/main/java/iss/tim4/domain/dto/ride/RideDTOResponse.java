package iss.tim4.domain.dto.ride;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RejectionDTO;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.driver.DriverRideDTO;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import iss.tim4.domain.dto.review.ReviewDTO;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Review;
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

    private Integer id;
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
    private String departure;
    private String destination;
    private ReviewDTO[] reviews;

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
        if(this.rejection != null) {
            this.rejection = new RejectionDTO(ride.getRejection());
        }
        Set<Route> routes = ride.getRoutes();
        RouteDTO[] locationDTOS = new RouteDTO[routes.size()];
        iter = 0;
        for(Route route : routes){
            locationDTOS[iter] = new RouteDTO(route);
            if(iter == 0){
                this.departure = route.getStartLocation().getAddress();
                this.destination = route.getEndLocation().getAddress();             // TODO: ako budemo implementirali da imamo vise ruta ovo nece biti end destination!
            }
            iter++;
        }
        this.locations = locationDTOS;
        Set<Review> reviews = ride.getReviews();
        ReviewDTO[] reviewDTOS = new ReviewDTO[reviews.size()];
        iter = 0;
        for(Review review : reviews){
            reviewDTOS[iter] = new ReviewDTO(review);
            iter++;
        }
        this.reviews = reviewDTOS;
    }
}
