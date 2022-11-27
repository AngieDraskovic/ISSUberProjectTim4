package iss.tim4.demo.repository;

import iss.tim4.demo.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class InMemoryUserRepository implements UserRepository{

    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, User> users = new ConcurrentHashMap<Long, User>();

    @Override
    public Collection<User> findAll() {
        users.put(1L, new User("Ana", "Draskovic", "image", "aa", "aa", "a", "a"));
        users.put(3L, new User("Ana", "Draskovic", "image", "aa", "aa", "a", "a"));
        return this.users.values();
    }

    @Override
    public User create(User user) {
        Long id = user.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            user.setId(id);
        }

        this.users.put(id, user);
        return user;
    }

    @Override
    public User findOne(Long id) {
        return this.users.get(id);
    }

    @Override
    public void delete(Long id) {
        this.users.remove(id);
    }

    @Override
    public User update(User user) {
        Long id = user.getId();

        if (id != null) {
            this.users.put(id, user);
        }

        return user;
    }
    }
