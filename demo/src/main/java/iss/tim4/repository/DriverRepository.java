package iss.tim4.repository;

import iss.tim4.domain.dto.DriverDTO;
import iss.tim4.domain.dto.UserDTO;

import java.util.Collection;

public interface DriverRepository {
    Collection<DriverDTO> findAll();

    UserDTO create(DriverDTO driver);

    UserDTO findOne(Long id);

    UserDTO update(DriverDTO driver);

    void delete(Long id);
}
