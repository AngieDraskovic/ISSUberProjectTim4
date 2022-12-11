package iss.tim4.domain.dto;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTOResult {

    private Long id;
    private Long driverId;
    private VehicleName vehicleType;
    private String model;
    private String licenseNumber;
    private LocationDTO currLocation;
    private Integer passengerSeats;
    private Boolean babyTransport;
    private Boolean petTransport;

    public VehicleDTOResult(Vehicle vehicle){
        this.id = vehicle.getId();
        this.driverId = vehicle.getDriver().getId();
        this.model = vehicle.getModel();
        this.vehicleType = vehicle.getVehicleName();
        this.licenseNumber = vehicle.getRegPlates();
        this.passengerSeats = vehicle.getNumSeats();
        this.currLocation = new LocationDTO(vehicle.getCurrLocation());
        this.babyTransport = vehicle.getBabyProof();
        this.petTransport = vehicle.getPetsAllowed();
    }
}
