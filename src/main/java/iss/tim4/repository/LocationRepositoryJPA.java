package iss.tim4.repository;

import iss.tim4.domain.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepositoryJPA extends JpaRepository<Location, Integer> {
}
