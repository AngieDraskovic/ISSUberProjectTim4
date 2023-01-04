package iss.tim4.repository;

import iss.tim4.domain.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RideRepositoryJPA extends JpaRepository<Ride,Integer> {

  @Query(value = "select ride.id,ride.driver_id, route.start_location_id, route.end_location_id from ride inner join route on route.id=ride.id;", nativeQuery = true)
  public List<Object[]> getRidesFromRoutes();



}
