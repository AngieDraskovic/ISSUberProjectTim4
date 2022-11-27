package iss.tim4.service;

import iss.tim4.domain.Passenger;
import iss.tim4.repository.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private InMemoryPassengerRepository passengerRepository;

    @Overridegit 
    public Collection<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger findOne(Long id) {
        return passengerRepository.findOne(id);
    }

    @Override
    public Passenger create(Passenger passenger) throws Exception {
        if (passenger.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        return passengerRepository.create(passenger);
    }

    @Override
    public Passenger update(Passenger passenger) throws Exception {
        Passenger passengerToUpdate = findOne(passenger.getId());
        if (passengerToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        passengerToUpdate.setName(passenger.getName());
        return passengerRepository.create(passengerToUpdate);
    }

    @Override
    public void delete(Long id) {
        passengerRepository.delete(id);
    }
}
