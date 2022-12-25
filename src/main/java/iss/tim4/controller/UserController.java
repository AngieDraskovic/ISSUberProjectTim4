package iss.tim4.controller;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.dto.user.*;
import iss.tim4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<UberPageDTO<UserDTO>> getAllUsers(Pageable pageable) {
        return new ResponseEntity<>(userService.getAllUsers(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride")
    public ResponseEntity<UberPageDTO<RideDTOResponse>> getUsersRides(Pageable pageable, @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getRidesOfUser(pageable, id), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(EmailPasswordDTO emailPasswordDTO) {
        return new ResponseEntity<>(userService.login(emailPasswordDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/message")
    public ResponseEntity<UberPageDTO<SentUserMessageDTO>> getUsersMessages(Pageable pageable,
                                                                            @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserMessages(pageable, id), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/message")
    public ResponseEntity<SentUserMessageDTO> postUsersMessages(CreateUserMessageDTO createUserMessageDTO,
                                                                             @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.createUserMessage(createUserMessageDTO, id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/block")
    public ResponseEntity<Void> putBlock(CreateUserMessageDTO createUserMessageDTO,
                                                                @PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/unblock")
    public ResponseEntity<Void> putUnblock(CreateUserMessageDTO createUserMessageDTO,
                                                                @PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/note")
    public ResponseEntity<UberPageDTO<UserNoteDTO>> getUsersNotes(Pageable pageable,
                                                                  @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserNote(pageable, id), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/note")
    public ResponseEntity<UserNoteDTO> postUsersNotes(CreateUserNoteDTO createUserNoteDTO,
                                                             @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.createUserNote(createUserNoteDTO, id), HttpStatus.OK);
    }
}
