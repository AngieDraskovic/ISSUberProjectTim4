package iss.tim4.service;

import iss.tim4.domain.model.PassengerActivation;
import iss.tim4.domain.model.Route;
import iss.tim4.repository.PassengerActivationRepository;
import iss.tim4.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerActivationService {
    @Autowired
    private PassengerActivationRepository passengerActivationRepository;

    public PassengerActivation findOne(Integer id) {
        return passengerActivationRepository.findById(id).orElseGet(null);
    }

    public PassengerActivation save(PassengerActivation passengerActivation) {
        return passengerActivationRepository.save(passengerActivation);
    }
}
