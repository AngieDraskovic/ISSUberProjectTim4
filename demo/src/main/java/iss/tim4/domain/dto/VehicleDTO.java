package iss.tim4.domain.dto;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id;
    private String model;
    private VehicleName vehicleName;
    private String regPlates;
    private Integer numSeats;
    private LocationDTO currLocation;
    private Boolean babyProof;
    private Boolean petsAllowed;

    public VehicleDTO(Vehicle vehicle){
        this.id = vehicle.getId();
        this.model = vehicle.getModel();
        this.vehicleName = vehicle.getVehicleName();
        this.regPlates = vehicle.getRegPlates();
        this.numSeats = vehicle.getNumSeats();
        this.currLocation = new LocationDTO(vehicle.getCurrLocation());
        this.babyProof = vehicle.getBabyProof();
        this.petsAllowed = vehicle.getPetsAllowed();
    }
}
