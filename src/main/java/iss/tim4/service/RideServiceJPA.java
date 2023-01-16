package iss.tim4.service;



import iss.tim4.domain.dto.OneRideOfPassengerDTO;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.domain.model.Ride;
import iss.tim4.repository.RideRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceJPA {

    @Autowired
    private RideRepositoryJPA rideRepositoryJPA;

    @Autowired
    VehicleTypeServiceJPA vehicleTypeServiceJPA;

    public Ride findOne(Integer id) {
        return rideRepositoryJPA.findById(id).orElse(null);
    }

    public List<Ride> findAll() {
        return rideRepositoryJPA.findAll();
    }

    public Page<Ride> findAll(Pageable page) {
        return rideRepositoryJPA.findAll(page);
    }

    public List<Ride> findByPassengerId(Integer passengerId) {
        return rideRepositoryJPA.findByPassengerId(passengerId);
    }

    public Ride save(Ride ride) {
        return rideRepositoryJPA.save(ride);
    }

    public void remove(Integer id) {
        rideRepositoryJPA.deleteById(id);
    }

    public List<Object[]> getRideWithLocation(){
        return rideRepositoryJPA.getRidesFromRoutes();
    }

    public UberPageDTO<OneRideOfPassengerDTO> getAllRides(Pageable pageable) {
        return new UberPageDTO<>(findAll(pageable).map(OneRideOfPassengerDTO::new));
    }

    public double calculateCost(RideDTORequest rideDTO) {
        double pricePerType = vehicleTypeServiceJPA.getPriceForVehicleType(rideDTO.getVehicleType());
        return pricePerType * rideDTO.getKilometers() * 120;

    }
}
