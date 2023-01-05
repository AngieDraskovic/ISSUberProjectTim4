package iss.tim4.repository;

import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PassengerRepositoryJPA extends JpaRepository<Passenger,Integer> {
}
