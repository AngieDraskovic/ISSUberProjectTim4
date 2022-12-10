package iss.tim4.repository;

import iss.tim4.domain.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepositoryJPA extends JpaRepository<Vehicle, Long> {

}
