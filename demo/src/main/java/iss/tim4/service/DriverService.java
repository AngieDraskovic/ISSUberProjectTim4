package iss.tim4.service;

import iss.tim4.domain.Driver;

import java.util.Collection;

public interface DriverService {

    Collection<Driver> findAll();

    Driver findOne(Long id);

    Driver create(Driver user) throws Exception;

    Driver update(Driver user) throws Exception;

    void delete(Long id);
}
