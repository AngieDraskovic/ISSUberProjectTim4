package iss.tim4.service;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.Review;
import iss.tim4.repository.ReviewRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceJPA {

    @Autowired
    private ReviewRepositoryJPA reviewRepositoryJPA;

    public Review findOne(Integer id) {
        return reviewRepositoryJPA.findById(id).orElse(null);
    }

    public List<Review> findAll() {
        return reviewRepositoryJPA.findAll();
    }

    public Page<Review> findAll(Pageable page) {
        return reviewRepositoryJPA.findAll(page);
    }

    public Review save(Review review) {
        return reviewRepositoryJPA.save(review);
    }

    public void remove(Integer id) {
        reviewRepositoryJPA.deleteById(id);
    }


}
