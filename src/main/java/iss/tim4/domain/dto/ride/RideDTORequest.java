package iss.tim4.domain.dto.ride;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDTORequest {
    private Boolean babyTransport;
    private Boolean petTransport;
    private PassengerRideDTO[] passengers;
    private RouteDTO[] locations;
    private VehicleName vehicleType;

}
