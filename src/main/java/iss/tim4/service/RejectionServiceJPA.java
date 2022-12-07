package iss.tim4.service;

import iss.tim4.domain.model.Rejection;
import iss.tim4.domain.model.VehicleType;
import iss.tim4.repository.RejectionRepositoryJPA;
import iss.tim4.repository.VehicleTypeRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RejectionServiceJPA {
    @Autowired
    private RejectionRepositoryJPA rejectionRepositoryJPA;

    public Rejection findOne(Integer id) {
        return rejectionRepositoryJPA.findById(id).orElseGet(null);
    }

    public Rejection save(Rejection rejection) {
        return rejectionRepositoryJPA.save(rejection);
    }
}
