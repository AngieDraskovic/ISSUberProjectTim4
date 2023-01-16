package iss.tim4.service;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.Ride;
import iss.tim4.domain.model.Vehicle;
import iss.tim4.repository.VehicleRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class VehicleServiceJPA {

    @Autowired
    private VehicleRepositoryJPA vehicleRepositoryJPA;

    @Autowired
    private RideServiceJPA rideServiceJPA;
    public Vehicle findOne(Integer id) {
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

    public void remove(Integer id) {
        vehicleRepositoryJPA.deleteById(id);
    }


    public void checkVehicleAvailability(){
        List<Object[]> listOfRideswithRoutes = rideServiceJPA.getRideWithLocation();
        for(Object[] o : listOfRideswithRoutes){
            Ride ride = rideServiceJPA.findOne(Integer.parseInt((o[0]).toString()));
            Object[] vehicleObject = vehicleRepositoryJPA.getDriversVehicle(ride.getDriver().getId());
            Vehicle v = findOne(Integer.parseInt(vehicleObject[0].toString()));
            if(ride.getStartTime().isBefore(LocalDateTime.now()) &&  ride.getEndTime().isAfter(LocalDateTime.now())) {
                System.out.println("Start time:" + ride.getStartTime());
                System.out.println("End time: " + ride.getEndTime());
                v.setAvailable(false);
            }
        }
    }
}
