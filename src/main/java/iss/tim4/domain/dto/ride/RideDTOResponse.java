package iss.tim4.domain.dto.ride;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RejectionDTO;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.driver.DriverRideDTO;
import iss.tim4.domain.dto.favourite.route.FavouriteRouteDTOResult;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import iss.tim4.domain.dto.review.ReviewDTOResult;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Review;
import iss.tim4.domain.model.Ride;
import iss.tim4.domain.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDTOResponse {

    private Integer id;
    @NotNull(message = "Field startTime is required!")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endTime;
    @NotNull (message = "Field totalCost is required!")
    private Double totalCost;
    @NotNull (message = "Field driver is required!")
    private DriverRideDTO driver;
    @NotNull (message = "Field estimatedTimeInMinutes is required!")
    private Double estimatedTimeInMinutes;
    @NotNull (message = "Field kilometers is required!")
    private Double kilometers;
    @NotNull (message = "Field status is required!")
    private RideStatus status;
    @Valid
    private RejectionDTO rejection;
    private Boolean babyTransport;
    private Boolean petTransport;
    @NotNull (message = "Field vehicleType is required!")
    private VehicleName vehicleType;
    @NotNull (message = "Field passengers is required!")
    private PassengerRideDTO[] passengers;
    @NotNull (message = "Field locations is required!")
    private RouteDTO[] locations;
    @NotEmpty (message = "Field departure is required!")
    private String departure;
    @NotNull (message = "Field destination is required!")
    private String destination;
    @Valid
    private ReviewDTOResult[] reviews;
    @Valid
    private FavouriteRouteDTOResult favouriteRoute;

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
        this.kilometers = ride.getKilometers();
        this.vehicleType = ride.getVehicleType().getVehicleName();
        this.babyTransport = ride.getBabyTransport();
        this.petTransport = ride.getPetTransport();
        this.status = ride.getStatus();
        if (ride.getRejection() != null) {
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
        ReviewDTOResult[] reviewDTOS = new ReviewDTOResult[reviews.size()];
        iter = 0;
        for(Review review : reviews){
            reviewDTOS[iter] = new ReviewDTOResult(review);
            iter++;
        }
        this.reviews = reviewDTOS;
//        if (ride.getFavouriteRoute() != null)
//            this.favouriteRoute = new FavouriteRouteDTOResult(ride.getFavouriteRoute());
    }
}
