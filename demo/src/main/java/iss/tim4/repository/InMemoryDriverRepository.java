package iss.tim4.repository;

import iss.tim4.domain.Driver;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryDriverRepository implements DriverRepository {
    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, Driver> drivers = new ConcurrentHashMap<Long, Driver>();

    @Override
    public Collection<Driver> findAll() {
        drivers.put(1L, new Driver("Ana", "Draskovic", "image", "0654233234", "a@gmail.com", "Majke Jugoiva 9", "xq"));
        drivers.put(2L, new Driver("Ana", "Draskovic", "image", "0654233234", "a@gmail.com", "Majke Jugoiva 9", "xq"));
        drivers.put(3L, new Driver("Milica", "Simic", "image", "065243243", "y@gmail.com", "Trg Slobode 5", "yt"));
        return this.drivers.values();
    }

    @Override
    public Driver create(Driver user) {
        Long id = user.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            user.setId(id);
        }

        this.drivers.put(id, user);
        return user;
    }

    @Override
    public Driver findOne(Long id) {
        return this.drivers.get(id);
    }

    @Override
    public void delete(Long id) {
        this.drivers.remove(id);
    }

    @Override
    public Driver update(Driver driver) {
        Long id = driver.getId();

        if (id != null) {
            this.drivers.put(id, driver);
        }

        return driver;
    }
}


