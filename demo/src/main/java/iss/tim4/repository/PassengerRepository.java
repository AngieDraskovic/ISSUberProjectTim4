package iss.tim4.repository;

import iss.tim4.domain.dto.PassengerDTO;
import iss.tim4.domain.dto.UserDTO;

import java.util.Collection;

public interface PassengerRepository {
    Collection<PassengerDTO> findAll();

    UserDTO create(PassengerDTO passenger);

    UserDTO findOne(Long id);

    UserDTO update(PassengerDTO passenger);

    void delete(Long id);

}
