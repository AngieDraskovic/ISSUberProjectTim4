package iss.tim4.service;

import iss.tim4.domain.model.Route;
import iss.tim4.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceJPA {
    @Autowired
    private RouteRepository routeRepositoryJPA;

    public Route findOne(Integer id) {
        return routeRepositoryJPA.findById(id).orElseGet(null);
    }

    public Route save(Route route) {
        return routeRepositoryJPA.save(route);
    }

}
