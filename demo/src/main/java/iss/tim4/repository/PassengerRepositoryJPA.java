package iss.tim4.repository;

import iss.tim4.domain.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PassengerRepositoryJPA extends JpaRepository<Passenger,Integer> {

}
