package iss.tim4.domain.dto.ride;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDTOExample {
    private PassengerDTOResult[] passengers;
    private RouteDTO[] routes;
    private boolean babyTransport;
    private boolean petTransport;
    private String vehicleName;
    private LocalDateTime startTime;
    private double estimatedTime;
    private double kilometers;


    public Integer getVehicleNameInt() {
        return VehicleName.valueOf(vehicleName).ordinal();
    }
}
