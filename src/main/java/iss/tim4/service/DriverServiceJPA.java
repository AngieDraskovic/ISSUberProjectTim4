package iss.tim4.service;


import iss.tim4.domain.dto.LocationDTO;
import iss.tim4.domain.dto.OneRideOfPassengerDTO;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;

import iss.tim4.domain.dto.ride.RideDTOExample;
import iss.tim4.domain.dto.ride.RideDTORequest;

import iss.tim4.domain.model.Driver;

import iss.tim4.domain.model.Ride;
import iss.tim4.domain.model.WorkingHours;
import iss.tim4.repository.DriverRepositoryJPA;

import iss.tim4.repository.RideRepositoryJPA;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DriverServiceJPA {

    @Autowired
    private DriverRepositoryJPA driverRepositoryJPA;

    @Autowired
    private WorkingHoursServiceJPA workingHoursServiceJPA;

    @Autowired
    private RideRepositoryJPA rideRepositoryJPA;
    public Driver findOne(Integer id) {
        return driverRepositoryJPA.findById(id).orElse(null);
    }

    public List<Driver> findAll() {
        return driverRepositoryJPA.findAll();
    }

    public Page<Driver> findAll(Pageable page) {
        return driverRepositoryJPA.findAll(page);
    }

    public Driver save(Driver driver) {
        return driverRepositoryJPA.save(driver);
    }

    public void remove(Integer id) {
        driverRepositoryJPA.deleteById(id);
    }


    public UberPageDTO<DriverDTOResult> getAllDrivers(Pageable pageable) {
        return new UberPageDTO<>(driverRepositoryJPA.findAll(pageable).map(DriverDTOResult::new));
    }

    public UberPageDTO<OneRideOfPassengerDTO> getRidesOfDriver(Pageable pageable, Integer userId) {
        Driver driver = findOne(userId);
        return new UberPageDTO<>(rideRepositoryJPA.findByPassengersId(pageable, userId).map(OneRideOfPassengerDTO::new));

    }

    public Driver findAvailableDriver(RideDTORequest rideDTO) {
        List<Driver> allDrivers = findAll();
        List<Driver> availableDrivers = new ArrayList<Driver>();    // Sve slobodne ovde pa cemo posle od njih najblizeg da nadjemo
        List<Driver> busyDrivers = new ArrayList<Driver>();     // Ako su svi zauzeti biramo onog koji je najblizi da zavrsi voznju
        HashMap<Driver, Ride> possibleBusyDrivers;
        for (Driver driver : allDrivers) {
            if (!driver.getActive() || driver.getBlocked())
                continue;
            if (!driver.compatibileVehicle(rideDTO))
                continue;
            if (outOfWorkingHours(driver))
                continue;
            if (driver.isAvailable(rideDTO))
                availableDrivers.add(driver);
            else
                busyDrivers.add(driver);
        }


        if (availableDrivers.size() > 0)
            return findNearestDriver(rideDTO.getLocations()[0].getDeparture(), availableDrivers);

        if (busyDrivers.size() > 0)
            possibleBusyDrivers = checkForPossibleBusyDrivers(busyDrivers, rideDTO);
        // TODO: Od zauzetih nadji onog koji ce najprije zavrsiti trenutnu voznju

        return null;
    }

    private HashMap<Driver, Ride> checkForPossibleBusyDrivers(List<Driver> busyDrivers, RideDTORequest newRide) {
        for (Driver driver : busyDrivers) {
            for (Ride ride : driver.getRides()) {
                if (driver.isBusy(ride, newRide))
                    return null;
            }
        }
        return null;
    }

    private Driver findFastestDriver(List<Driver> busyDrivers, RideDTORequest rideDTO) {
        return null;
    }

    public Driver findNearestDriver(LocationDTO location, List<Driver> avaliableDrivers) {
        double minDistance = Double.MAX_VALUE;
        Driver nearestDriver = null;
        for (Driver driver : avaliableDrivers) {
            double distance = driver.getVehicle().getCurrLocation().distanceTo(location);
            if (distance < minDistance) {
                minDistance = distance;
                nearestDriver = driver;
            }
        }
        return nearestDriver;
    }

    private boolean outOfWorkingHours(Driver driver) {
        List<WorkingHours> driverWorkingHours = workingHoursServiceJPA.findByDriverId(driver.getId());
        int minutesSum = 0;
        for (WorkingHours workingHours : driverWorkingHours) {
            if (workingHours.getEnd() == null)
                workingHours.setEnd(LocalDateTime.now());
            Duration duration = Duration.between(workingHours.getStart(), workingHours.getEnd());
            minutesSum += duration.toMinutes();
        }
        return minutesSum > 60*8;
    }

}
