package iss.tim4.service;

import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.ride.RideDTOExample;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.domain.model.Route;
import iss.tim4.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Set<Route> getRoutes(RideDTORequest rideDTO) {
        Set<Route> routes = new HashSet<>();
        for (RouteDTO routeDTO : rideDTO.getLocations()) {
            Route route = new Route(routeDTO, rideDTO.getKilometers());
            routes.add(route);
        }
        return routes;
    }
}
