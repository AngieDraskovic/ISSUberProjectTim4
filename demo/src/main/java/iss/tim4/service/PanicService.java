package iss.tim4.service;

import iss.tim4.domain.model.Panic;

import java.util.Collection;

public interface PanicService {
    Collection<Panic> findAll();
}
