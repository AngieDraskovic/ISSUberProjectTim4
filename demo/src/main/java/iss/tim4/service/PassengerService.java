package iss.tim4.service;

import iss.tim4.domain.Passenger;

import java.util.Collection;

public interface PassengerService {

    Collection<Passenger> findAll();

    Passenger findOne(Long id);

    Passenger create(Passenger user) throws Exception;

    Passenger update(Passenger user) throws Exception;

    void delete(Long id);
}
