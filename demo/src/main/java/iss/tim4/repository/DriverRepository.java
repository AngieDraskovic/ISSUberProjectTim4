package iss.tim4.repository;

import iss.tim4.domain.Driver;
import iss.tim4.domain.User;

import java.util.Collection;

public interface DriverRepository {
    Collection<Driver> findAll();

    User create(Driver driver);

    User findOne(Long id);

    User update(Driver driver);

    void delete(Long id);
}
