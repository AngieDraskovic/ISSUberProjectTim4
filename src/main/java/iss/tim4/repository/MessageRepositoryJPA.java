package iss.tim4.repository;

import iss.tim4.domain.model.Location;
import iss.tim4.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepositoryJPA extends JpaRepository<Message, Integer> {
}
