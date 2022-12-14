package iss.tim4.domain.dto;

import iss.tim4.domain.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTOResponse {

    private String vehicleType;
    private String model;
    private String licenseNumber;
    private LocationDTO currentLocation;
    private Integer passengerSeats;
    private Boolean babyTransport;
    private Boolean petTransport;


    public VehicleDTOResponse(Vehicle vehicle){
        this.model = vehicle.getModel();
        this.vehicleType = vehicle.getVehicleName().toString();
        this.licenseNumber = vehicle.getRegPlates();
        this.passengerSeats = vehicle.getNumSeats();
        this.currentLocation = new LocationDTO(vehicle.getCurrLocation());
        this.babyTransport = vehicle.getBabyProof();
        this.petTransport = vehicle.getPetsAllowed();
    }

}
