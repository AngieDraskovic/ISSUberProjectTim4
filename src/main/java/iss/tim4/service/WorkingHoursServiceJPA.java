package iss.tim4.service;

import iss.tim4.domain.model.WorkingHours;
import iss.tim4.repository.WorkingHoursRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingHoursServiceJPA {

    @Autowired
    private WorkingHoursRepositoryJPA workingHoursRepositoryJPA;

    public WorkingHours findOne(Integer id) {
        return workingHoursRepositoryJPA.findById(id).orElseGet(null);
    }

    public List<WorkingHours> findAll() {
        return workingHoursRepositoryJPA.findAll();
    }

    public Page<WorkingHours> findAll(Pageable page) {
        return workingHoursRepositoryJPA.findAll(page);
    }

    public WorkingHours save(WorkingHours workingHours) {
        return workingHoursRepositoryJPA.save(workingHours);
    }

    public void remove(Integer id) {
        workingHoursRepositoryJPA.deleteById(id);
    }

    public List<WorkingHours> findByDriverId(Integer id) { return workingHoursRepositoryJPA.findByDriverId(id); }

}
