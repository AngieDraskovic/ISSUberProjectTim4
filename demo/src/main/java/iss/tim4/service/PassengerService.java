package iss.tim4.service;

import iss.tim4.domain.dto.PassengerDTO;

import java.util.Collection;

public interface PassengerService {

    Collection<PassengerDTO> findAll();

    PassengerDTO findOne(Long id);

    PassengerDTO create(PassengerDTO user) throws Exception;

    PassengerDTO update(PassengerDTO user) throws Exception;

    void delete(Long id);
}
