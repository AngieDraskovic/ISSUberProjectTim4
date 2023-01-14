package iss.tim4.repository;

import iss.tim4.domain.model.Activation;
import iss.tim4.domain.model.DriverDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationRepositoryJPA extends JpaRepository<Activation, String> {
}
