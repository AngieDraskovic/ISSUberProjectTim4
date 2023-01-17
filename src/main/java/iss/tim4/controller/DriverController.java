package iss.tim4.controller;

import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.driver.DriverDTORequest;
import iss.tim4.domain.dto.driver.DriverDTOResponse;
import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.dto.driver.document.DriverDocumentDTOResponse;
import iss.tim4.domain.dto.driver.document.DriverDocumentDTOResult;
import iss.tim4.domain.dto.driver.request.DriverDTOUpdate;
import iss.tim4.domain.dto.driver.request.DriverRequestDTORequest;
import iss.tim4.domain.dto.driver.request.DriverRequestDTOResult;
import iss.tim4.domain.dto.working.hours.WorkingHoursDTORequest;
import iss.tim4.domain.dto.working.hours.WorkingHoursDTOResponse;
import iss.tim4.domain.dto.working.hours.WorkingHoursDTOResult;
import iss.tim4.domain.model.*;
import iss.tim4.errors.UberException;
import iss.tim4.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/driver")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DriverController {
    @Autowired
    private DriverServiceJPA driverServiceJPA;

    @Autowired
    private DriverDocumentServiceJPA driverDocumentServiceJPA;

    @Autowired
    private VehicleServiceJPA vehicleServiceJPA;

    @Autowired
    private WorkingHoursServiceJPA workingHoursServiceJPA;

    @Autowired
    private DriverRequestServiceJPA driverRequestServiceJPA;

    @Autowired
    private UserServiceJPA userService;

    // #1 create new driver - POST api/driver
    @PostMapping(consumes = "application/json")
    public ResponseEntity<DriverDTOResult> createDriver(@Valid @RequestBody DriverDTOResponse driverDTOResponse) throws Exception {
        if(userService.getUser(driverDTOResponse.getEmail())!=null){
            throw new UberException(HttpStatus.BAD_REQUEST, "User with that email already exists! ");
        }
        if(userService.getUserByTelephoneNumber(driverDTOResponse.getTelephoneNumber())!=null){
            throw new UberException(HttpStatus.BAD_REQUEST, "User with that telephone number already exists! ");
        }
        Driver driver = new Driver(driverDTOResponse);
        Driver savedDriver = driverServiceJPA.save(driver);
        DriverDTOResult driverDTOResult = new DriverDTOResult(savedDriver);
        return new ResponseEntity<DriverDTOResult>(driverDTOResult, HttpStatus.OK);
    }


    // #2 get all drivers - GET api/driver
    @GetMapping
    public ResponseEntity<UberPageDTO<DriverDTOResult>> getDrivers(Pageable pageable) {
        return new ResponseEntity<>(driverServiceJPA.getAllDrivers(pageable), HttpStatus.OK);
    }


    // #3 get driver by id - GET api/driver/1
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDTOResult> getDriver(@PathVariable("id") Integer id) {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DriverDTOResult driverDTOResult = new DriverDTOResult(driver);
        return new ResponseEntity<DriverDTOResult>(driverDTOResult, HttpStatus.OK);
    }


    // #4 update driver - GET api/driver/1
    @PutMapping(value = "/{id}")
    public ResponseEntity<DriverDTOResult> updateDriver(@RequestBody DriverDTOUpdate driverDTOResponse, @PathVariable Integer id) throws UberException {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(userService.getUser(driverDTOResponse.getEmail())!=null){
            throw new UberException(HttpStatus.BAD_REQUEST, "User with that email already exists! ");
        }
        if(userService.getUserByTelephoneNumber(driverDTOResponse.getTelephoneNumber())!=null){
            throw new UberException(HttpStatus.BAD_REQUEST, "User with that telephone number already exists! ");
        }
        //driver.updateDriver(driverDTOResponse);
        driverServiceJPA.save(driver);
        DriverDTOResult driverDTOResult = new DriverDTOResult(driver);
        return new ResponseEntity<DriverDTOResult>(driverDTOResult, HttpStatus.OK);
    }


    // #5 get driver documents - GET api/driver/1/documents
    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DriverDocumentDTOResult>> getDriverDocuments(@PathVariable("id") Integer id) {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<DriverDocument> documents = driver.getDocuments();
        List<DriverDocumentDTOResult> documentDTOS = new ArrayList<>();
        for (DriverDocument d : documents) {
            documentDTOS.add(new DriverDocumentDTOResult(d));
        }
        return new ResponseEntity<>(documentDTOS, HttpStatus.OK);
    }


    // #6 delete driver documents - DELETE api/driver/1/documents
    @DeleteMapping(value = "/document/{id}") // TODO edit this method as it should delete onlu specific document
    public ResponseEntity<Void> deleteDriverDocuments(@PathVariable Integer id) {
        DriverDocument document = driverDocumentServiceJPA.findOne(id);
        if(document == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for(DriverDocument d : document.getDriver().getDocuments()){
            if(Objects.equals(d.getId(), document.getId())){
                document.getDriver().getDocuments().remove(d);
            }
        }
        driverDocumentServiceJPA.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // #7 create driver documents - POST api/driver/1/documents
    @PostMapping(value = "/{id}/documents", consumes = "application/json")
    public ResponseEntity<DriverDocumentDTOResult> createDriverDocument(@RequestBody DriverDocumentDTOResponse driverDocumentDTOResponse, @PathVariable Integer id) {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DriverDocument driverDocument = new DriverDocument(driverDocumentDTOResponse, driver);
        driverDocumentServiceJPA.save(driverDocument);
        DriverDocumentDTOResult driverDocumentDTOResult = new DriverDocumentDTOResult(driverDocument);
        return new ResponseEntity<DriverDocumentDTOResult>(driverDocumentDTOResult, HttpStatus.OK);
    }


    // #8 get driver vehicle - GET api/driver/1/vehicle
    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTOResult> getDriverVehicle(@PathVariable("id") Integer id) throws UberException {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(driver.getVehicle()==null){
            throw new UberException(HttpStatus.BAD_REQUEST, "Vehicle is not assigned!");
        }
        VehicleDTOResult vehicleDTO = new VehicleDTOResult(driver.getVehicle());
        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }


    // #9 create driver vehicle - POST api/driver/1/vehicle
    @PostMapping(value = "/{id}/vehicle", consumes = "application/json")
    public ResponseEntity<VehicleDTOResult> createDriverVehicle(@RequestBody VehicleDTOResponse vehicleDTOResponse, @PathVariable Integer id) {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Vehicle vehicle = new Vehicle(driver, vehicleDTOResponse);
        vehicleServiceJPA.save(vehicle);
        driver.setVehicle(vehicle);
        driverServiceJPA.save(driver);
        VehicleDTOResult vehicleDTOResult = new VehicleDTOResult(vehicle);
        return new ResponseEntity<VehicleDTOResult>(vehicleDTOResult, HttpStatus.OK);
    }


    // #10 update driver vehicle - PUT api/driver/1/vehicle
    @PutMapping(value = "/{id}/vehicle", consumes = "application/json")
    public ResponseEntity<VehicleDTOResult> updateDriverVehicle(@RequestBody VehicleDTOResponse vehicleDTOResponse, @PathVariable Integer id) {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Vehicle vehicle = new Vehicle(driver, vehicleDTOResponse);
        vehicleServiceJPA.save(vehicle);
        VehicleDTOResult vehicleDTOResult = new VehicleDTOResult(vehicle);
        return new ResponseEntity<VehicleDTOResult>(vehicleDTOResult, HttpStatus.OK);
    }

    // #11 get driver working hours - GET api/driver/1/working-hours
    @GetMapping(value = "/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)                      //TODO Mora biti pageable
    public ResponseEntity<WorkingHoursDTORequest> getDriverWorkingHours(@PathVariable("id") Integer id) {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<WorkingHours> workingHoursList = workingHoursServiceJPA.findByDriverId(id);

        WorkingHoursDTOResult[] workingHoursDTOResults = new WorkingHoursDTOResult[workingHoursList.size()];
        for (int i = 0; i < workingHoursDTOResults.length; i++){
            workingHoursDTOResults[i] = new WorkingHoursDTOResult(workingHoursList.get(i));
        }

        WorkingHoursDTORequest workingHoursDTORequest = new WorkingHoursDTORequest(workingHoursDTOResults);
        return new ResponseEntity<>(workingHoursDTORequest, HttpStatus.OK);
    }


    // #12 create driver working-hours - POST api/driver/1/working-hours
    @PostMapping(value = "/{id}/working-hour", consumes = "application/json")
    public ResponseEntity<WorkingHoursDTOResult> createDriverVehicle(@RequestBody WorkingHoursDTOResponse workingHoursDTOResponse, @PathVariable Integer id) {
        Driver driver = driverServiceJPA.findOne(id);
        if(driver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        WorkingHours workingHours = new WorkingHours(driver, workingHoursDTOResponse);
        workingHoursServiceJPA.save(workingHours);
        WorkingHoursDTOResult workingHoursDTOResult = new WorkingHoursDTOResult(workingHours);
        return new ResponseEntity<>(workingHoursDTOResult, HttpStatus.OK);
    }


    // #13 get driver rides - GET api/driver/1/ride
    @GetMapping(value = "/{id}/ride")
    public ResponseEntity<UberPageDTO<OneRideOfPassengerDTO>> getPassengerRides(@PathVariable("id") Integer id, Pageable pageable) {
        Driver driver = driverServiceJPA.findOne(id);
        if (driver == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(driverServiceJPA.getRidesOfDriver(pageable, id), HttpStatus.OK);
    }



    // #14 get driver vehicle - GET api/driver/working-hour/1
    @GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHoursDTOResult> getWorkingHour(@PathVariable("working-hour-id") Integer id) {
        WorkingHours workingHours = workingHoursServiceJPA.findOne(id);
        if(workingHours==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        WorkingHoursDTOResult workingHoursDTOResult = new WorkingHoursDTOResult(workingHours);
        return new ResponseEntity<>(workingHoursDTOResult, HttpStatus.OK);
    }


    // #15 update driver vehicle - PUT api/driver/working-hour/1
    @PutMapping(value = "/working-hour/{working-hour-id}", consumes = "application/json")
    public ResponseEntity<WorkingHoursDTOResult> updateWorkingHour(@RequestBody WorkingHoursDTOResponse workingHoursDTOResponse, @PathVariable("working-hour-id") Integer id) {
        WorkingHours workingHours = workingHoursServiceJPA.findOne(id);
        if(workingHours==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        workingHours.update(workingHoursDTOResponse);
        workingHoursServiceJPA.save(workingHours);
        WorkingHoursDTOResult workingHoursDTOResult = new WorkingHoursDTOResult(workingHours);
        return new ResponseEntity<WorkingHoursDTOResult>(workingHoursDTOResult, HttpStatus.OK);
    }



    // #16 create new driver request - POST api/driver/driver-request/
    @PostMapping(value = "/driver-request", consumes = "application/json")
    public ResponseEntity<DriverRequestDTOResult> createDriverRequest(@RequestBody DriverRequestDTORequest driverRequestDTORequest) {
        System.out.println("USAOOO");
        Driver driver = driverServiceJPA.findOne(driverRequestDTORequest.getDriverId().intValue());
        Vehicle vehicle = vehicleServiceJPA.findOne(driverRequestDTORequest.getVehicleId());
        DriverRequest driverRequest = new DriverRequest(driver, vehicle, driverRequestDTORequest);
        driverRequestServiceJPA.save(driverRequest);
        DriverRequestDTOResult driverRequestDTOResult = new DriverRequestDTOResult(driverRequest);
        return new ResponseEntity<>(driverRequestDTOResult, HttpStatus.OK);
    }



    // #17 get driver request - GET api/driver/driver-request/1
    @GetMapping(value = "/driver-request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverRequestDTOResult> getDriverRequest(@PathVariable("id") Integer id) {
        DriverRequest driverRequest = driverRequestServiceJPA.findOne(id);
        DriverRequestDTOResult driverRequestDTOResult = new DriverRequestDTOResult(driverRequest);
        return new ResponseEntity<>(driverRequestDTOResult, HttpStatus.OK);
    }


    //    @Scheduled(cron = "0 13 21 * * *")
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")    // Izvrsava se svake ponoci
    public void scheduledMethod() {
        workingHoursServiceJPA.deleteAllWorkingHours();
        List<WorkingHours> ll = workingHoursServiceJPA.findAll();
        System.out.println(ll.size());
    }
}
