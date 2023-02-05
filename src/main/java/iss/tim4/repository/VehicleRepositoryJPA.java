package iss.tim4.repository;

import iss.tim4.domain.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleRepositoryJPA extends JpaRepository<Vehicle,Integer> {

    @Query(value = "select vehicle_id from users where id= :driverId", nativeQuery=true)
    public Object[] getDriversVehicle(@Param("driverId") Integer driverId);
}
