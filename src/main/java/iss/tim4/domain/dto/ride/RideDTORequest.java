package iss.tim4.domain.dto.ride;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDTORequest {
    private Boolean babyTransport;
    private Boolean petTransport;
    private PassengerDTOResult[] passengers;
    private RouteDTO[] locations;
    private VehicleName vehicleType;
    private LocalDateTime startTime;
    private double estimatedTime;
    private double kilometers;

}
