package iss.tim4.repository;

import iss.tim4.domain.dto.DriverDTO;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryDriverRepository implements DriverRepository {
    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, DriverDTO> drivers = new ConcurrentHashMap<Long, DriverDTO>();

    @Override
    public Collection<DriverDTO> findAll() {
        drivers.put(4L, new DriverDTO(4L,"Ana", "Draskovic", "image", "0654233234", "a@gmail.com", "Majke Jugoiva 9", "xq", true, true, null));
        drivers.put(6L, new DriverDTO(6L,"Milica", "Simic", "image", "065243243", "y@gmail.com", "Trg Slobode 5", "yt", true, true, null));
        return this.drivers.values();
    }

    @Override
    public DriverDTO create(DriverDTO user) {
        Long id = user.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            user.setId(id);
        }

        this.drivers.put(id, user);
        return user;
    }

    @Override
    public DriverDTO findOne(Long id) {
        return this.drivers.get(id);
    }

    @Override
    public void delete(Long id) {
        this.drivers.remove(id);
    }

    @Override
    public DriverDTO update(DriverDTO driver) {
        Long id = driver.getId();

        if (id != null) {
            this.drivers.put(id, driver);
        }

        return driver;
    }
}


