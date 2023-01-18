package iss.tim4.domain.dto;

import iss.tim4.domain.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTOResponse {
    @Size(min = 3, max = 30, message = "Field vehicle type format is not valid!")
    private String vehicleType;
    @Size(min = 1, max = 30, message = "Field model format is not valid!")
    private String model;
    @NotNull (message = "Field license number is required!")
    private String licenseNumber;
    @NotNull (message = "Field current location is required!")
    private LocationDTO currentLocation;
    @NotNull (message = "Field current location is required!")
    @Min(value = 1, message = "Field passengerSeats is grater than 1!")
    @Max(value = 8, message = "Field passengerSeats is less than 8!")
    private Integer passengerSeats;
    @NotNull (message = "Field current location is required!")
    private Boolean babyTransport;
    @NotNull (message = "Field current location is required!")
    private Boolean petTransport;
    private Boolean available;


    public VehicleDTOResponse(Vehicle vehicle){
        this.model = vehicle.getModel();
        this.vehicleType = vehicle.getVehicleName().toString();
        this.licenseNumber = vehicle.getRegPlates();
        this.passengerSeats = vehicle.getNumSeats();
        this.currentLocation = new LocationDTO(vehicle.getCurrLocation());
        this.babyTransport = vehicle.getBabyProof();
        this.petTransport = vehicle.getPetsAllowed();
        this.available = vehicle.getAvailable();
    }

}
