package iss.tim4.repository;

import iss.tim4.domain.model.PassengerActivation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerActivationRepository extends JpaRepository<PassengerActivation, Integer> {
}
