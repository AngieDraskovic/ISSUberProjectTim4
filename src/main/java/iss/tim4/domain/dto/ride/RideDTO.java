package iss.tim4.domain.dto.ride;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.dto.RejectionDTO;
import iss.tim4.domain.dto.VehicleTypeDTO;
import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDTO {

    private Integer id;

    @NotNull (message = "Field startTime is required!")
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @NotNull (message = "Field price is required!")
    private Double price;
    private DriverDTOResult driver;
    @NotNull (message = "Field estimatedTime is required!")
    private Double estimatedTime;
    @NotNull (message = "Field kilometers is required!")
    private Double kilometers;
    @NotNull (message = "Field rideStatus is required!")
    private RideStatus rideStatus;
    @Valid
    private RejectionDTO rejectionDTO;
    private Boolean panic;
    private Boolean babyProof;
    private Boolean pets;
    @Valid
    private VehicleTypeDTO vehicleType;

    public RideDTO(Ride ride) {
        this.id = ride.getId();
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.driver = new DriverDTOResult(ride.getDriver());
        this.kilometers = ride.getKilometers();
        this.estimatedTime = ride.getEstimatedTimeInMinutes();
        this.rideStatus = ride.getStatus();
        this.rejectionDTO = new RejectionDTO(ride.getRejection());
        this.panic = ride.getPanic() != null;
        this.babyProof = ride.getBabyTransport();
        this.pets = ride.getPetTransport();
        this.vehicleType = new VehicleTypeDTO(ride.getVehicleType());
    }

}
