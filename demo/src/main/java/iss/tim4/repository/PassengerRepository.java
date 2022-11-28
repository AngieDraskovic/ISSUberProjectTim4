package iss.tim4.repository;

import iss.tim4.domain.Passenger;
import iss.tim4.domain.User;

import java.util.Collection;

public interface PassengerRepository {
    Collection<Passenger> findAll();

    User create(Passenger passenger);

    User findOne(Long id);

    User update(Passenger passenger);

    void delete(Long id);

}
