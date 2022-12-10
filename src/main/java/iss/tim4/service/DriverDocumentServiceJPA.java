package iss.tim4.service;

import iss.tim4.domain.model.DriverDocument;
import iss.tim4.repository.DriverDocumentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverDocumentServiceJPA {

    @Autowired
    private DriverDocumentRepositoryJPA driverDocumentRepositoryJPA;

    public DriverDocument findOne(Integer id) {
        return driverDocumentRepositoryJPA.findById(id).orElseGet(null);
    }

    public List<DriverDocument> findAll() {
        return driverDocumentRepositoryJPA.findAll();
    }

    public Page<DriverDocument> findAll(Pageable page) {
        return driverDocumentRepositoryJPA.findAll(page);
    }

    public DriverDocument save(DriverDocument driverDocument) {
        return driverDocumentRepositoryJPA.save(driverDocument);
    }

    public void remove(Integer id) {
        driverDocumentRepositoryJPA.deleteById(id);
    }

    public Integer removeByDriverId(Long driverId) { return driverDocumentRepositoryJPA.deleteByDriverId(driverId); }


}
