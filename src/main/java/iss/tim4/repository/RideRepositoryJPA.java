package iss.tim4.repository;

import iss.tim4.domain.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RideRepositoryJPA extends JpaRepository<Ride,Integer> {

  @Query(value = "select ride.id,ride.driver_id, route.start_location_id, route.end_location_id from ride inner join route on route.id=ride.id;", nativeQuery = true)
  public List<Object[]> getRidesFromRoutes();

   // select sta
   // imam vozilo i njegov id,
   // ako je vozilo slobodno odmah dobavi njegove koordinate
   // select * from ride where vehicle.id = ride.vehicle.id and ride.status=available;

   // ja za ride da dobavim rutu (s.l i e.l)


  /* Dobavljamo voznje jednog passengera gdje mu prosledjujemo id. */
  @Query("SELECT r FROM Ride r JOIN r.passengers p WHERE p.id = :passengerId")
  List<Ride> findByPassengerId(@Param("passengerId") Integer passengerId);

}
