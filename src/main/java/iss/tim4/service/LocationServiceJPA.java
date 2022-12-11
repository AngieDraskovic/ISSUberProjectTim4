package iss.tim4.service;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.Location;
import iss.tim4.repository.LocationRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceJPA {

    @Autowired
    private LocationRepositoryJPA locationRepositoryJPA;

    public Location findOne(Integer id) {
        return locationRepositoryJPA.findById(id).orElseGet(null);
    }

    public List<Location> findAll() {
        return locationRepositoryJPA.findAll();
    }

    public Page<Location> findAll(Pageable page) {
        return locationRepositoryJPA.findAll(page);
    }

    public Location save(Location location) {
        return locationRepositoryJPA.save(location);
    }

    public void remove(Integer id) {
        locationRepositoryJPA.deleteById(id);
    }

}
