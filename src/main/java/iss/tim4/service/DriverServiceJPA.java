package iss.tim4.service;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.Passenger;
import iss.tim4.repository.DriverRepositoryJPA;
import iss.tim4.repository.PassengerRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceJPA {

    @Autowired
    private DriverRepositoryJPA driverRepositoryJPA;

    public Driver findOne(Integer id) {
        return driverRepositoryJPA.findById(id).orElseGet(null);
    }

    public List<Driver> findAll() {
        return driverRepositoryJPA.findAll();
    }

    public Page<Driver> findAll(Pageable page) {
        return driverRepositoryJPA.findAll(page);
    }

    public Driver save(Driver driver) {
        return driverRepositoryJPA.save(driver);
    }

    public void remove(Integer id) {
        driverRepositoryJPA.deleteById(id);
    }

}
