package iss.tim4.demo.repository;

import iss.tim4.demo.domain.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findAll();

    User create(User user);

    User findOne(Long id);

    User update(User greeting);

    void delete(Long id);

}
