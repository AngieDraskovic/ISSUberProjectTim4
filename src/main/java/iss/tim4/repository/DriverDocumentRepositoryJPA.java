package iss.tim4.repository;

import iss.tim4.domain.model.DriverDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DriverDocumentRepositoryJPA extends JpaRepository<DriverDocument, Integer> {

    @Modifying
    @Transactional
    @Query("Delete From DriverDocument where driver.id = :driverId")
    public Integer deleteByDriverId(@Param("driverId") Long driverId);
}
