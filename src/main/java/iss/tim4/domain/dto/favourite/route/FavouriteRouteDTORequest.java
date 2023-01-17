package iss.tim4.domain.dto.favourite.route;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import iss.tim4.domain.model.FavouriteRoute;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteRouteDTORequest {

    @NotEmpty (message = "Field favoriteName is required!")
    private String favoriteName;
    @NotNull (message = "Field locations is required!")
    private RouteDTO[] locations;
    @NotNull (message = "Field passengers is required!")
    private PassengerRideDTO[] passengers;
    @NotNull (message = "Field vehicleType is required!")
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public FavouriteRouteDTORequest(FavouriteRoute favouriteRoute) {
        this.favoriteName = favouriteRoute.getFavouriteName();

        setLocationsFromList(favouriteRoute.getLocations());
        setPassengersFromList(favouriteRoute.getPassengers());
        this.vehicleType = favouriteRoute.getVehicleType();
        this.babyTransport = favouriteRoute.getBabyTransport();
        this.petTransport = favouriteRoute.getPetTransport();
    }


    private void setLocationsFromList(Set<Route> locations) {
        this.locations = new RouteDTO[locations.size()];
        int i = 0;
        for (Route location : locations) {
            this.locations[i] = new RouteDTO(location);
            i++;
        }
    }

    private void setPassengersFromList(Set<Passenger> passengers) {
        this.passengers = new PassengerRideDTO[passengers.size()];
        int i = 0;
        for (Passenger passenger : passengers) {
            this.passengers[i] = new PassengerRideDTO(passenger);
            i++;
        }
    }


}
