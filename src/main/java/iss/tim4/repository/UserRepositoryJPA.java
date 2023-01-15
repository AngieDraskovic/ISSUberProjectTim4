package iss.tim4.repository;

import iss.tim4.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<User, Integer> {
    public User findOneByEmail(String email);

    boolean existsByEmail(String username);
}
