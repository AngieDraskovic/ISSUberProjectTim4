package iss.tim4.repository;

import iss.tim4.domain.dto.PassengerDTO;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class InMemoryPassengerRepository implements PassengerRepository{

    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, PassengerDTO> passengers = new ConcurrentHashMap<Long, PassengerDTO>();

    @Override
    public Collection<PassengerDTO> findAll() {
        passengers.put(1L, new PassengerDTO(1L,"Isidora", "Tadic", "image", "aa", "aa", "a", "a", true, true));
        passengers.put(3L, new PassengerDTO(3L, "Igor", "Milosevic", "image", "aa", "aa", "a", "a", true, true));
        return this.passengers.values();
    }

    @Override
    public PassengerDTO create(PassengerDTO user) {
        Long id = user.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            user.setId(id);
        }

        this.passengers.put(id, user);
        return user;
    }

    @Override
    public PassengerDTO findOne(Long id) {
        return this.passengers.get(id);
    }

    @Override
    public void delete(Long id) {
        this.passengers.remove(id);
    }

    @Override
    public PassengerDTO update(PassengerDTO passenger) {
        Long id = passenger.getId();

        if (id != null) {
            this.passengers.put(id, passenger);
        }

        return passenger;
    }
    }
