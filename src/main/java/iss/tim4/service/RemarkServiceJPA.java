package iss.tim4.service;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.Remark;
import iss.tim4.repository.RemarkRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemarkServiceJPA {
    @Autowired
    private RemarkRepositoryJPA remarkRepositoryJPA;

    public Remark findOne(Integer id) {
        return remarkRepositoryJPA.findById(id).orElse(null);
    }

    public List<Remark> findAll() {
        return remarkRepositoryJPA.findAll();
    }

    public Page<Remark> findAll(Pageable page) {
        return remarkRepositoryJPA.findAll(page);
    }

    public Remark save(Remark remark) {
        return remarkRepositoryJPA.save(remark);
    }

    public void remove(Integer id) {
        remarkRepositoryJPA.deleteById(id);
    }

}
