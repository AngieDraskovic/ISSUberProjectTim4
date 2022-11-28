package iss.tim4.service;

import iss.tim4.domain.Driver;
import iss.tim4.repository.InMemoryDriverRepository;
import iss.tim4.repository.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class DriverServiceImpl implements DriverService{
    @Autowired
    private InMemoryDriverRepository driverRepository;

    @Override
    public Collection<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver findOne(Long id) {
        return driverRepository.findOne(id);
    }

    @Override
    public Driver create(Driver driver) throws Exception {
        if (driver.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        return driverRepository.create(driver);
    }

    @Override
    public Driver update(Driver driver) throws Exception {
        Driver driverToUpdate = findOne(driver.getId());
        if (driverToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        driverToUpdate.setName(driver.getName());
        return driverRepository.create(driverToUpdate);
    }

    @Override
    public void delete(Long id) {
        driverRepository.delete(id);
    }
}
