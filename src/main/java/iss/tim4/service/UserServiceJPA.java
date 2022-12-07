package iss.tim4.service;

import iss.tim4.domain.model.Route;
import iss.tim4.domain.model.User;
import iss.tim4.repository.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJPA {
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    public User findOne(Integer id) {
        return userRepositoryJPA.findById(id).orElseGet(null);
    }

    public User save(User user) {
        return userRepositoryJPA.save(user);
    }
}
