package iss.tim4.repository;

import iss.tim4.domain.dto.PassengerDTO;
import iss.tim4.domain.dto.UserDTO;

import java.util.Collection;

public interface PassengerRepository {
    Collection<PassengerDTO> findAll();

    PassengerDTO create(PassengerDTO passenger);

    PassengerDTO findOne(Long id);

    PassengerDTO update(PassengerDTO passenger);

    void delete(Long id);

}
