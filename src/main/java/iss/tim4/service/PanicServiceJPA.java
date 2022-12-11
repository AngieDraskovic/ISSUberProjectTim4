package iss.tim4.service;

import iss.tim4.domain.dto.PanicDTO;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.repository.PanicRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PanicServiceJPA implements PanicService {
    @Autowired
    PanicRepositoryJPA panicRepositoryJPA;

    public Collection<PanicDTO> findAll() {
        return panicRepositoryJPA.findAll().stream().map(PanicDTO::new).toList();
    }

    @Override
    public UberPageDTO<PanicDTO> findAll(Pageable pageable) {
        return new UberPageDTO<>(panicRepositoryJPA.findAll(pageable).map(PanicDTO::new));
    }
}
