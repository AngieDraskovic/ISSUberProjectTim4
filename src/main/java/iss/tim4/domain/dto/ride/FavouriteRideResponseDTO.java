package iss.tim4.domain.dto.ride;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;

import java.time.LocalDate;

public class FavouriteRideResponseDTO {
    private Integer id;
    private String favoriteName;
    private LocalDate scheduledTime;
    private RouteDTO[] locations;
    private PassengerDTOResult[] passengers;            // passengers who have it as a favourite location
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;


}
