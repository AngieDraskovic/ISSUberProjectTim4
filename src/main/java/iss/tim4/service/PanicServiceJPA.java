package iss.tim4.service;

import iss.tim4.domain.model.Panic;
import iss.tim4.repository.PanicRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PanicServiceJPA implements PanicService {
    @Autowired
    PanicRepositoryJPA panicRepositoryJPA;

    public Collection<Panic> findAll() {
        return panicRepositoryJPA.findAll();
    }
}
