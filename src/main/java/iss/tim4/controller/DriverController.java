package iss.tim4.controller;

import iss.tim4.domain.dto.DriverDTO;
import iss.tim4.domain.model.Driver;
import iss.tim4.service.DriverServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
@AllArgsConstructor
public class DriverController {
    @Autowired
    private DriverServiceJPA driverServiceJPA;


    @GetMapping
    public ResponseEntity<List<DriverDTO>> getDrivers() {
        List<Driver> drivers = driverServiceJPA.findAll();


        // we have to convert passengers to passengers DTOs
        List<DriverDTO> driverDTOS = new ArrayList<>();
        for (Driver d : drivers) {
            driverDTOS.add(new DriverDTO(d));
        }

        return new ResponseEntity<>(driverDTOS, HttpStatus.OK);

    }

    /*

    // get by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDTO> getDriver(@PathVariable("id") Long id) {
        DriverDTO driver = driverService.findOne(id);

        if (driver == null) {
            return new ResponseEntity<DriverDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<DriverDTO>(driver , HttpStatus.OK);
    }

    // create
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDTO> createDriver(@RequestBody DriverDTO driver) throws Exception {
        DriverDTO savedDriver = driverService.create(driver);
        return new ResponseEntity<DriverDTO>(savedDriver, HttpStatus.CREATED);
    }

    // update --> for now only changes the name of driver
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody DriverDTO driver, @PathVariable Long id)
            throws Exception {
        DriverDTO driverForUpdate = driverService.findOne(id);
        driverForUpdate.copyValues(driver);

        DriverDTO updatedDriver =  driverService.update(driverForUpdate);

        if (updatedDriver == null) {
            return new ResponseEntity<DriverDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<DriverDTO>(updatedDriver, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DriverDTO> deleteDriver(@PathVariable("id") Long id) {
        driverService.delete(id);
        return new ResponseEntity<DriverDTO>(HttpStatus.NO_CONTENT);
    }
*/


}