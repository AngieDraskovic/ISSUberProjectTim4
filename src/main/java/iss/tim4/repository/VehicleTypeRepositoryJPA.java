package iss.tim4.repository;

import iss.tim4.domain.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepositoryJPA extends JpaRepository<VehicleType, Integer> {
}
