package iss.tim4.service;

import iss.tim4.domain.model.Location;
import iss.tim4.domain.model.Route;
import iss.tim4.repository.LocationRepositoryJPA;
import iss.tim4.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceJPA {
    @Autowired
    private LocationRepositoryJPA locationRepositoryJPA;

    public Location findOne(Integer id) {
        return locationRepositoryJPA.findById(id).orElseGet(null);
    }

    public Location save(Location location) {
        return locationRepositoryJPA.save(location);
    }
}
