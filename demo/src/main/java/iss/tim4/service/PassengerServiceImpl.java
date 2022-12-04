package iss.tim4.service;

import iss.tim4.domain.dto.PassengerDTO;
import iss.tim4.repository.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private InMemoryPassengerRepository passengerRepository;

    @Override
    public Collection<PassengerDTO> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public PassengerDTO findOne(Long id) {
        return passengerRepository.findOne(id);
    }

    @Override
    public PassengerDTO create(PassengerDTO passenger) throws Exception {
        if (passenger.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        return passengerRepository.create(passenger);
    }

    @Override
    public PassengerDTO update(PassengerDTO passenger) throws Exception {
        PassengerDTO passengerToUpdate = findOne(passenger.getId());
        if (passengerToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        passengerToUpdate.copyValues(passenger);
        return passengerRepository.update(passengerToUpdate);
    }

    @Override
    public void delete(Long id) {
        passengerRepository.delete(id);
    }
}
