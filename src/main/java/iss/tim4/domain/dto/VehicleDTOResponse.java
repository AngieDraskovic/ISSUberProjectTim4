package iss.tim4.domain.dto;

import iss.tim4.domain.VehicleName;
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

    private VehicleName vehicleType;
    @Size(min = 1, max = 10, message = "Field model format is not valid!")
    private String model;
    @NotEmpty (message = "Field license number is required!")
    private String licenseNumber;

    private LocationDTO currentLocation;

    @Min(value = 1, message = "Field passengerSeats cannot be lesser than 1!")
    @Max(value = 8, message = "Field passengerSeats cannot be great than 8!")
    private Integer passengerSeats;
    @NotNull (message = "Field babyTransport is required!")
    private Boolean babyTransport;
    @NotNull (message = "Field petTransport is required!")
    private Boolean petTransport;
    private Boolean available;


    public VehicleDTOResponse(Vehicle vehicle){
        this.model = vehicle.getModel();
        this.vehicleType = vehicle.getVehicleName();
        this.licenseNumber = vehicle.getRegPlates();
        this.passengerSeats = vehicle.getNumSeats();
        this.currentLocation = new LocationDTO(vehicle.getCurrLocation());
        this.babyTransport = vehicle.getBabyProof();
        this.petTransport = vehicle.getPetsAllowed();
        this.available = vehicle.getAvailable();
    }

}
