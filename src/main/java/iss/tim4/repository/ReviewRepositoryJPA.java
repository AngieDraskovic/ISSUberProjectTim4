package iss.tim4.repository;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepositoryJPA extends JpaRepository<Review, Integer> {

}
