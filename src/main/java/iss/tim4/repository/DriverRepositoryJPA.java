package iss.tim4.repository;

import iss.tim4.domain.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DriverRepositoryJPA extends JpaRepository<Driver, Integer> {


}
