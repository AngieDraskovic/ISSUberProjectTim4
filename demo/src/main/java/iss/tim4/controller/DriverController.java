package iss.tim4.controller;

import iss.tim4.domain.Driver;
import iss.tim4.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
