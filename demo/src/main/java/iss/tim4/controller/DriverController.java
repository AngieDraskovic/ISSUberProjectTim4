package iss.tim4.controller;

import iss.tim4.domain.Driver;
import iss.tim4.domain.Passenger;
import iss.tim4.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/vozac")
@AllArgsConstructor
public class DriverController {
    @Autowired
    private DriverService driverService;

    // get all
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Driver>> getDrivers() {
        Collection<Driver> drivers = driverService.findAll();
        return new ResponseEntity<Collection<Driver>>(drivers, HttpStatus.OK);
    }


    // get by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> getDriver(@PathVariable("id") Long id) {
        Driver driver = driverService.findOne(id);

        if (driver == null) {
            return new ResponseEntity<Driver>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Driver>(driver , HttpStatus.OK);
    }

    // create
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) throws Exception {
        Driver savedDriver = driverService.create(driver);
        return new ResponseEntity<Driver>(savedDriver, HttpStatus.CREATED);
    }

    // update --> for now only changes the name of driver
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver, @PathVariable Long id)
            throws Exception {
        Driver driverForUpdate = driverService.findOne(id);
        driverForUpdate.copyValues(driver);

        Driver updatedDriver =  driverService.update(driverForUpdate);

        if (updatedDriver == null) {
            return new ResponseEntity<Driver>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Driver>(updatedDriver, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable("id") Long id) {
        driverService.delete(id);
        return new ResponseEntity<Driver>(HttpStatus.NO_CONTENT);
    }



}