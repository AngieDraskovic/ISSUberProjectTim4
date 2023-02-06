package iss.tim4.service;

import iss.tim4.domain.model.Message;
import iss.tim4.repository.MessageRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceJPA {

    @Autowired
    MessageRepositoryJPA messageRepositoryJPA;

    public Message findOne(Integer id) {
        return messageRepositoryJPA.findById(id).orElse(null);
    }

    public List<Message> findAll() {
        return messageRepositoryJPA.findAll();
    }

    public Page<Message> findAll(Pageable page) {
        return messageRepositoryJPA.findAll(page);
    }

    public Message save(Message message) {
        return messageRepositoryJPA.save(message);
    }

    public void remove(Integer id) {
        messageRepositoryJPA.deleteById(id);
    }
    public List<Message> findByRideId(Integer rideId) { return messageRepositoryJPA.findByRideId(rideId); }

}
