package iss.tim4.controller;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.dto.passenger.PassengerDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.dto.user.*;
import iss.tim4.domain.model.DriverRequest;
import iss.tim4.domain.model.Role;
import iss.tim4.domain.model.User;
import iss.tim4.errors.UberException;
import iss.tim4.service.DriverRequestServiceJPA;
import iss.tim4.service.PassengerServiceJPA;
import iss.tim4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DriverRequestServiceJPA driverService;
    @Autowired
    PassengerServiceJPA passengerService;

    @GetMapping(value = "/me")
    public ResponseEntity<UserMoreDTO> userDTOResponseEntity(Principal principal) {
        User user = userService.getUser(principal.getName());
        UserMoreDTO userMoreDTO = new UserMoreDTO();
        userMoreDTO.setUser(new UserDTO(user));
        userMoreDTO.setRole(user.getRole());
        if (user.getRole() == Role.PASSENGER) {
            userMoreDTO.setPassenger(new PassengerDTOResult(passengerService.findOne(user.getId())));
        }
        if (user.getRole() == Role.DRIVER) {
            userMoreDTO.setDriver(driverService.findOne(user.getId()));
        }

        return new ResponseEntity<>(userMoreDTO, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UberPageDTO<UserDTO>> getAllUsers(Pageable pageable) {
        return new ResponseEntity<>(userService.getAllUsers(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride")
    public ResponseEntity<UberPageDTO<RideDTOResponse>> getUsersRides(
            Principal user,
            Pageable pageable,
            @PathVariable("id") Integer id
    ) throws UberException {

        var actualUser = userService.getUser(user.getName());
        if (actualUser.getRole() != Role.ADMIN && !Objects.equals(actualUser.getId(), id)) {
            throw new UberException(HttpStatus.FORBIDDEN, "Wrong ID!");
        }
        return new ResponseEntity<>(userService.getRidesOfUser(pageable, id), HttpStatus.OK);
    }

//    @GetMapping(value = "/{id}/message")
//    public ResponseEntity<UberPageDTO<SentUserMessageDTO>> getUsersMessages(Pageable pageable,
//                                                                            @PathVariable("id") Long id) {
//        return new ResponseEntity<>(userService.getUserMessages(pageable, id), HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/{id}/message")
//    public ResponseEntity<SentUserMessageDTO> postUsersMessages(CreateUserMessageDTO createUserMessageDTO,
//                                                                             @PathVariable("id") Long id) {
//        return new ResponseEntity<>(userService.createUserMessage(createUserMessageDTO, id), HttpStatus.OK);
//    }
//
//    @PutMapping(value = "/{id}/block")
//    public ResponseEntity<Void> putBlock(CreateUserMessageDTO createUserMessageDTO,
//                                                                @PathVariable("id") Long id) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping(value = "/{id}/unblock")
//    public ResponseEntity<Void> putUnblock(CreateUserMessageDTO createUserMessageDTO,
//                                                                @PathVariable("id") Long id) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping(value = "/{id}/note")
//    public ResponseEntity<UberPageDTO<UserNoteDTO>> getUsersNotes(Pageable pageable,
//                                                                  @PathVariable("id") Long id) {
//        return new ResponseEntity<>(userService.getUserNote(pageable, id), HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/{id}/note")
//    public ResponseEntity<UserNoteDTO> postUsersNotes(CreateUserNoteDTO createUserNoteDTO,
//                                                             @PathVariable("id") Long id) {
//        return new ResponseEntity<>(userService.createUserNote(createUserNoteDTO, id), HttpStatus.OK);
//    }
}
