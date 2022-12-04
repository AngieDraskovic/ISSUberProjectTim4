package iss.tim4.repository;


import iss.tim4.domain.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride,Integer> {
}
