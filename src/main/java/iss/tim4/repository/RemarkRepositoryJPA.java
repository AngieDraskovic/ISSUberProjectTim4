package iss.tim4.repository;

import iss.tim4.domain.model.Remark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemarkRepositoryJPA extends JpaRepository<Remark, Integer> {
    Page<Remark> findByUserId(Integer id, Pageable pageable);
}
