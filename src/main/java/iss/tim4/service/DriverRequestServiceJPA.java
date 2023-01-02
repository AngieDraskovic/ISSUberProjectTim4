package iss.tim4.service;

import iss.tim4.domain.model.DriverRequest;
import iss.tim4.repository.DriverRequestRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverRequestServiceJPA {
    @Autowired
    private DriverRequestRepositoryJPA driverRequestRepositoryJPA;

    public DriverRequest findOne(Integer id) {
        return driverRequestRepositoryJPA.findById(id).orElse(null);
    }

    public List<DriverRequest> findAll() {
        return driverRequestRepositoryJPA.findAll();
    }

    public Page<DriverRequest> findAll(Pageable page) {
        return driverRequestRepositoryJPA.findAll(page);
    }

    public DriverRequest save(DriverRequest driverRequest) {
        return driverRequestRepositoryJPA.save(driverRequest);
    }

    public void remove(Integer id) {
        driverRequestRepositoryJPA.deleteById(id);
    }

}
