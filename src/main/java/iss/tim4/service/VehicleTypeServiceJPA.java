package iss.tim4.service;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.model.Vehicle;
import iss.tim4.domain.model.VehicleType;
import iss.tim4.repository.VehicleTypeRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleTypeServiceJPA {
    @Autowired
    private VehicleTypeRepositoryJPA vehicleTypeRepositoryJPA;

    public VehicleType findOne(Integer id) {
        return vehicleTypeRepositoryJPA.findById(id).orElseGet(null);
    }

    public VehicleType save(VehicleType vehicleType) {
        return vehicleTypeRepositoryJPA.save(vehicleType);
    }

    public Double getPriceForVehicleType(VehicleName vehicleName) {
        return vehicleTypeRepositoryJPA.findPriceByVehicleName(vehicleName);
    }

    public VehicleType findByVehicleName(VehicleName vehicleName) {
        return vehicleTypeRepositoryJPA.findByVehicleName(vehicleName);
    }
}
