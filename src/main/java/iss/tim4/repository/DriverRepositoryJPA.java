package iss.tim4.repository;

import iss.tim4.domain.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepositoryJPA extends JpaRepository<Driver, Integer> {
}
