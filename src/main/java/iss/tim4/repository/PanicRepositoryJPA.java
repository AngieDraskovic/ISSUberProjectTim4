package iss.tim4.repository;

import iss.tim4.domain.model.Panic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanicRepositoryJPA extends JpaRepository<Panic, Integer> {

}
