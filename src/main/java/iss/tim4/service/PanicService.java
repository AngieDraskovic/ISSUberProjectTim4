package iss.tim4.service;

import iss.tim4.domain.dto.PanicDTO;
import iss.tim4.domain.dto.UberPageDTO;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface PanicService {
    Collection<PanicDTO> findAll();

    UberPageDTO<PanicDTO> findAll(Pageable pageable);
}
