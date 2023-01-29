package iss.tim4.repository;

import iss.tim4.domain.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WorkingHoursRepositoryJPA extends JpaRepository<WorkingHours, Integer> {

    @Query("Select wh From WorkingHours wh where wh.driver.id = :driverId")
    public List<WorkingHours> findByDriverId(@Param("driverId") Integer driverId);

    @Modifying
    @Query(value = "DELETE FROM WorkingHours")
    public void deleteAllWorkingHours();
}
