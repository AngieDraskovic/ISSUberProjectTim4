package iss.tim4.service;

import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.PassengerActivation;
import iss.tim4.domain.model.Route;
import iss.tim4.errors.UberException;
import iss.tim4.repository.PassengerActivationRepository;
import iss.tim4.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class PassengerActivationService {
    @Autowired
    private PassengerActivationRepository passengerActivationRepository;
    @Autowired
    private PassengerServiceJPA passengerServiceJPA;

    public void activate(Integer id) throws UberException {
        PassengerActivation passengerActivation = passengerActivationRepository.findById(id)
                .orElseThrow(
                        () -> new UberException(HttpStatus.NOT_FOUND, "Activation with entered id does not exist!")
                );
        ZoneOffset zdt = ZoneOffset.UTC;
        if (LocalDateTime.now().toInstant(zdt).toEpochMilli() -
                passengerActivation.getCreationDate().toInstant(zdt).toEpochMilli()
                > 10 * 60 * 1000) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Activation expired. Register again!");
        }
        Passenger passenger = passengerActivation.getPassenger();
        passenger.setActive(Boolean.TRUE);
        passengerServiceJPA.save(passenger);
        passengerActivationRepository.delete(passengerActivation);
    }

    public PassengerActivation save(PassengerActivation passengerActivation) {
        return passengerActivationRepository.save(passengerActivation);
    }
}
