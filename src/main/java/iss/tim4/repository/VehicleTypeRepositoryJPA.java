package iss.tim4.repository;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleTypeRepositoryJPA extends JpaRepository<VehicleType, Integer> {

    @Query("SELECT vt.price FROM VehicleType vt WHERE vt.vehicleName = :vehicleName")
    public Double findPriceByVehicleName(@Param("vehicleName") VehicleName vehicleName);

    @Query("SELECT vt FROM VehicleType vt WHERE vt.vehicleName = :vehicleName")
    public VehicleType findByVehicleName(@Param("vehicleName") VehicleName vehicleName);
}
