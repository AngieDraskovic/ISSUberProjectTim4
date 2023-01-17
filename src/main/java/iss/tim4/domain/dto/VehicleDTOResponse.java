package iss.tim4.domain.dto;

import iss.tim4.domain.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTOResponse {

    @NotNull (message = "Field vehicleType is required!")
    private String vehicleType;
    @NotEmpty (message = "Field model is required!")
    private String model;
    @NotEmpty (message = "Field licenseNumber is required!")    // Postoji li neki sablon po kome se registracija vozila
    private String licenseNumber;                               // pravi, treba li regex za to ili reg moze biti razlicitih formata
    private LocationDTO currentLocation;        // Ako kreiramo vozilo tek, nema trenutnu lokaciju
    @Min(value = 2, message = "Field passengerSeats is grater than 2!")
    @Max(value = 8, message = "Field passengerSeats is less than 8!")
    private Integer passengerSeats;
    private Boolean babyTransport;
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
