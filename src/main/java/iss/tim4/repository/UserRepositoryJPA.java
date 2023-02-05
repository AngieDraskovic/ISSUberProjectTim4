package iss.tim4.repository;

import iss.tim4.domain.model.Ride;
import iss.tim4.domain.model.Role;
import iss.tim4.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import java.util.List;

public interface UserRepositoryJPA extends JpaRepository<User, Integer> {
    public User findOneByEmail(String email);


    public User findOnByTelephoneNumber(String telephoneNumber);

    boolean existsByEmail(String username);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") Role role);


}
