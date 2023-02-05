package iss.tim4.domain.dto.ride;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnregisteredRideDTORequest {
    private Boolean babyTransport;
    private Boolean petTransport;
    @NotNull (message = "Field locations is required!")
    private RouteDTO[] locations;
    @NotNull (message = "Field vehicleType is required!")
    private VehicleName vehicleType;
}
