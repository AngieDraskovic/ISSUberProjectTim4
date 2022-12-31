package iss.tim4.domain.dto.ride;

import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDTOExample {
    private PassengerDTOResult[] passengers;
    private RouteDTO[] routes;
    private boolean babyTransport;
    private boolean petTransport;
    private String VehicleName;
}
