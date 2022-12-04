package iss.tim4.service;

import iss.tim4.domain.dto.DriverDTO;

import java.util.Collection;

public interface DriverService {

    Collection<DriverDTO> findAll();

    DriverDTO findOne(Long id);

    DriverDTO create(DriverDTO user) throws Exception;

    DriverDTO update(DriverDTO user) throws Exception;

    void delete(Long id);
}
