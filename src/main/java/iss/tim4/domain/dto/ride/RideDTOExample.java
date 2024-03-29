package iss.tim4.domain.dto.ride;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDTOExample {
    private boolean babyTransport;
    private boolean petTransport;
    @Valid
    private PassengerDTOResult[] passengers;
    @Valid
    private RouteDTO[] locations;

    @NotNull (message = "Field vehicleName is required!")
    private String vehicleName;
    @NotNull (message = "Field startTime is required!")
    private LocalDateTime startTime;
    private double estimatedTime;
    private double kilometers;


    public Integer getVehicleNameInt() {
        return VehicleName.valueOf(vehicleName).ordinal();
    }
}
