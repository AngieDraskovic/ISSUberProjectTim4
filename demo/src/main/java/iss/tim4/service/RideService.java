package iss.tim4.service;


import iss.tim4.domain.model.Ride;
import iss.tim4.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    public Ride findOne(Integer id) {
        return rideRepository.findById(id).orElseGet(null);
    }

    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    public Page<Ride> findAll(Pageable page) {
        return rideRepository.findAll(page);
    }

    public Ride save(Ride ride) {
        return rideRepository.save(ride);
    }

    public void remove(Integer id) {
        rideRepository.deleteById(id);
    }

}
