package iss.tim4.domain.dto;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeDTO {

    private Long id;
    private VehicleName vehicleName;
    private Double price;

    public VehicleTypeDTO(VehicleType vehicleType) {
        this.id = vehicleType.getId();
        this.vehicleName = vehicleType.getVehicleName();
        this.price = vehicleType.getPrice();
    }
}
