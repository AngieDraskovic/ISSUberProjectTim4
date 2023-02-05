package iss.tim4.controller;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.dto.message.MessageDTORequest;
import iss.tim4.domain.dto.message.MessageDTOResult;
import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.Message;
import iss.tim4.domain.model.User;
import iss.tim4.service.MessageServiceJPA;
import iss.tim4.service.RideServiceJPA;
import iss.tim4.service.UserServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {

    @Autowired
    private RideServiceJPA rideServiceJPA;

    @Autowired
    private UserServiceJPA userServiceJPA;

    @Autowired
    private MessageServiceJPA messageServiceJPA;


    @PostMapping(consumes = "application/json")
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<MessageDTOResult> createMessage(@Valid @RequestBody MessageDTORequest messageDTO) throws Exception {

        User sender = userServiceJPA.getUserById(messageDTO.getSender().getId());
        User receiver = userServiceJPA.getUserById(messageDTO.getReceiver().getId());

        Message message = new Message(messageDTO);
        message.setSender(sender);
        message.setReceiver(receiver);

        messageServiceJPA.save(message);

        MessageDTOResult messageDTOResult = new MessageDTOResult(message);

        return new ResponseEntity<MessageDTOResult>(messageDTOResult, HttpStatus.OK);
    }


    @GetMapping(value = "/{rideId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'PASSENGER', 'DRIVER')")
    public ResponseEntity<List<MessageDTOResult>> getRideMessages(@PathVariable Integer rideId) {

        List<Message> messages = messageServiceJPA.findByRideId(rideId);

        List<MessageDTOResult> messageDTOResults = new ArrayList<>();
        for (Message message : messages) {
            messageDTOResults.add(new MessageDTOResult(message));
        }

        return new ResponseEntity<>(messageDTOResults, HttpStatus.OK);
    }


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'PASSENGER', 'DRIVER')")
    public ResponseEntity<List<MessageDTOResult>> getAllMessages() {

        List<Message> messages = messageServiceJPA.findAll();

        List<MessageDTOResult> messageDTOResults = new ArrayList<>();
        for (Message message : messages) {
            messageDTOResults.add(new MessageDTOResult(message));
        }

        return new ResponseEntity<>(messageDTOResults, HttpStatus.OK);
    }


}
