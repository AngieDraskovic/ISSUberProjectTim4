package iss.tim4.demo.service;

import iss.tim4.demo.repository.InMemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import iss.tim4.demo.domain.User;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private InMemoryUserRepository userRepository;

    @Override
    public Collection<User> findAll() {
        Collection<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User findOne(Long id) {
        User user = userRepository.findOne(id);
        return user;
    }

    @Override
    public User create(User user) throws Exception {
        if (user.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        User savedUser = userRepository.create(user);
        return savedUser;
    }

    @Override
    public User update(User user) throws Exception {
        User userToUpdate = findOne(user.getId());
        if (userToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        userToUpdate.setName(user.getName());
        User updatedGreeting =userRepository.create(userToUpdate);
        return updatedGreeting;
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
