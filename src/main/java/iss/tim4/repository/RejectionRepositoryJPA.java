package iss.tim4.repository;

import iss.tim4.domain.model.Rejection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectionRepositoryJPA extends JpaRepository<Rejection, Integer> {
}
