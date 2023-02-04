package iss.tim4.domain.dto.ride;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDTORequest {

    @NotNull (message = "Field vehicleType is required!")
    private VehicleName vehicleType;
    private Boolean babyTransport;
    private Boolean petTransport;
    private PassengerDTOResult[] passengers;
    @Valid
    private RouteDTO[] locations;

    @NotNull (message = "Field startTime is required!")
    private LocalDateTime startTime;

    @DecimalMin(value = "0.0", inclusive = true, message = "EstimatedTime must be above 0.0 minutes")
    private Double estimatedTime;

    @NotNull (message = "Field kilometers is required!")
    @Min(value=0)
    private double kilometers;
    private Integer agreementCode;

}
