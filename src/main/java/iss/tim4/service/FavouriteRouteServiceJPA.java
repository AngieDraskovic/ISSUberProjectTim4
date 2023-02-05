package iss.tim4.service;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.FavouriteRoute;
import iss.tim4.repository.DriverRepositoryJPA;
import iss.tim4.repository.FavouriteRouteRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteRouteServiceJPA {

    @Autowired
    private FavouriteRouteRepositoryJPA favouriteRouteRepositoryJPA;

    public FavouriteRoute findOne(Integer id) {
        return favouriteRouteRepositoryJPA.findById(id).orElseGet(null);
    }

    public List<FavouriteRoute> findAll() {
        return favouriteRouteRepositoryJPA.findAll();
    }

    public Page<FavouriteRoute> findAll(Pageable page) {
        return favouriteRouteRepositoryJPA.findAll(page);
    }

    public FavouriteRoute save(FavouriteRoute driver) {
        return favouriteRouteRepositoryJPA.save(driver);
    }

    public void remove(Integer id) {
        favouriteRouteRepositoryJPA.deleteById(id);
    }

    public void removeRouteFromFavourites(Integer passengerId, String startAddress, String endAddress) {
        favouriteRouteRepositoryJPA.removeRouteFromFavourites(passengerId, startAddress, endAddress);
    }
}
