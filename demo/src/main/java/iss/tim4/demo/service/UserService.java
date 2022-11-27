package iss.tim4.demo.service;

import iss.tim4.demo.domain.User;

import java.util.Collection;

public interface UserService {

    Collection<User> findAll();

    User findOne(Long id);

    User create(User user) throws Exception;

    User update(User user) throws Exception;

    void delete(Long id);
}
