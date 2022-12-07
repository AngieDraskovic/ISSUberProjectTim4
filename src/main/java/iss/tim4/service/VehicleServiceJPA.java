package iss.tim4.service;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.Vehicle;
import iss.tim4.repository.VehicleRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceJPA {

    @Autowired
    private VehicleRepositoryJPA vehicleRepositoryJPA;

    public Vehicle findOne(Long id) {
        return vehicleRepositoryJPA.findById(id).orElseGet(null);
    }

    public List<Vehicle> findAll() {
        return vehicleRepositoryJPA.findAll();
    }

    public Page<Vehicle> findAll(Pageable page) {
        return vehicleRepositoryJPA.findAll(page);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepositoryJPA.save(vehicle);
    }

    public void remove(Long id) {
        vehicleRepositoryJPA.deleteById(id);
    }
}
