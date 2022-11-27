package iss.tim4.repository;

import iss.tim4.domain.Passenger;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class InMemoryPassengerRepository implements PassengerRepository{

    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, Passenger> passengers = new ConcurrentHashMap<Long, Passenger>();

    @Override
    public Collection<Passenger> findAll() {
        passengers.put(1L, new Passenger("Ana", "Draskovic", "image", "aa", "aa", "a", "a"));
        passengers.put(3L, new Passenger("Ananas", "Draskovic", "image", "aa", "aa", "a", "a"));
        return this.passengers.values();
    }

    @Override
    public Passenger create(Passenger user) {
        Long id = user.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            user.setId(id);
        }

        this.passengers.put(id, user);
        return user;
    }

    @Override
    public Passenger findOne(Long id) {
        return this.passengers.get(id);
    }

    @Override
    public void delete(Long id) {
        this.passengers.remove(id);
    }

    @Override
    public Passenger update(Passenger passenger) {
        Long id = passenger.getId();

        if (id != null) {
            this.passengers.put(id, passenger);
        }

        return passenger;
    }
    }
