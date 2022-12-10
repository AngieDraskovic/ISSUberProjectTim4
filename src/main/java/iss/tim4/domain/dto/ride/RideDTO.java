package iss.tim4.domain.dto.ride;

import iss.tim4.domain.RideStatus;
import iss.tim4.domain.dto.RejectionDTO;
import iss.tim4.domain.dto.VehicleTypeDTO;
import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDTO {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
    private DriverDTOResult driver;
    private Double estimatedTime;
    private RideStatus rideStatus;
    private RejectionDTO rejectionDTO;
    private Boolean panic;
    private Boolean babyProof;
    private Boolean pets;
    private VehicleTypeDTO vehicleType;

    public RideDTO(Ride ride) {
        this.id = ride.getId();
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.driver = new DriverDTOResult(ride.getDriver());
        this.estimatedTime = ride.getEstimatedTimeInMinutes();
        this.rideStatus = ride.getStatus();
        this.rejectionDTO = new RejectionDTO(ride.getRejection());
        this.panic = ride.getPanic();
        this.babyProof = ride.getBabyTransport();
        this.pets = ride.getPetTransport();
        this.vehicleType = new VehicleTypeDTO(ride.getVehicleType());
    }

}
