package iss.tim4.service;



import iss.tim4.domain.RideStatus;
import iss.tim4.domain.dto.OneRideOfPassengerDTO;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.domain.model.Ride;
import iss.tim4.repository.RideRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RideServiceJPA {

    @Autowired
    private RideRepositoryJPA rideRepositoryJPA;

    @Autowired
    private VehicleTypeServiceJPA vehicleTypeServiceJPA;

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


    // NISAM OVU TESTIRALA
    public List<Object[]> getRideWithLocation(){
        return rideRepositoryJPA.getRidesFromRoutes();
    }

    public double calculateCost(RideDTORequest rideDTO) {
        if(rideDTO.getKilometers() < 0) {
            throw new IllegalArgumentException("Kilometers can not be negative");
        }
        System.out.println(rideDTO.getVehicleType());
        double pricePerType = vehicleTypeServiceJPA.getPriceForVehicleType(rideDTO.getVehicleType());
        return pricePerType * rideDTO.getKilometers() * 120;
    }

    public List<Ride> filterRidesByDate(LocalDateTime startDate, LocalDateTime endDate, Set<Ride> rides){
        List<Ride> filteredList = new ArrayList<>();
        for(Ride r : rides){
            if((r.getStartTime().isAfter(startDate) || r.getStartTime().equals(startDate)) &&
                    ((r.getStartTime().isBefore(endDate) || r.getStartTime().equals(endDate)))){
                filteredList.add(r);
            }
        }

        return filteredList;
    }

    public List<Ride> filterListOfRidesByDate(LocalDateTime startDate, LocalDateTime endDate, List<Ride> rides){
        List<Ride> filteredList = new ArrayList<>();
        for(Ride r : rides){
            if((r.getStartTime().isAfter(startDate) || r.getStartTime().equals(startDate)) &&
                    ((r.getStartTime().isBefore(endDate) || r.getStartTime().equals(endDate)))){
                filteredList.add(r);
            }
        }

        return filteredList;
    }

    public List<Ride> getActiveRides(){
        return rideRepositoryJPA.findActiveRides(RideStatus.ACTIVE);
    }



}
