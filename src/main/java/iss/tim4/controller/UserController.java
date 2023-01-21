package iss.tim4.controller;

import iss.tim4.domain.MessageType;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.ChangePasswordDTO;
import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.ResetPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.dto.user.CreateUserMessageDTO;
import iss.tim4.domain.dto.user.SentUserMessageDTO;
import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.dto.user.UserMoreDTO;
import iss.tim4.domain.model.Message;
import iss.tim4.domain.model.Remark;
import iss.tim4.domain.model.Role;
import iss.tim4.domain.model.User;
import iss.tim4.errors.UberException;
import iss.tim4.repository.MessageRepositoryJPA;
import iss.tim4.repository.RemarkRepositoryJPA;
import iss.tim4.repository.RideRepositoryJPA;
import iss.tim4.repository.UserRepositoryJPA;
import iss.tim4.security.jwt.JwtTokenUtil;
import iss.tim4.service.DriverRequestServiceJPA;
import iss.tim4.service.PassengerServiceJPA;
import iss.tim4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;
    @Autowired
    private DriverRequestServiceJPA driverService;
    @Autowired
    private PassengerServiceJPA passengerService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RemarkRepositoryJPA remarkRepositoryJPA;
    @Autowired
    private MessageRepositoryJPA messageRepositoryJPA;
    @Autowired
    private RideRepositoryJPA rideRepositoryJPA;

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

    @PutMapping(value = "/{id}/changePassword")
    public ResponseEntity<Void> changePassword(
            @PathVariable("id") Integer id,
            Principal user,
            @RequestBody ChangePasswordDTO passwords
    ) throws UberException {
        var actualUser = userService.getUser(user.getName());
        if (actualUser.getRole() != Role.ADMIN && !Objects.equals(actualUser.getId(), id)) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        userService.changePassword(id, passwords);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/resetPassword")
    public ResponseEntity<Void> sendEmail(
            @PathVariable("id") Integer id
    ) throws UberException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        userService.resetPassword(user.getEmail());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/resetPassword")
    public ResponseEntity<Void> resetPassword(
            @PathVariable("id") Integer id,
            @RequestBody ResetPasswordDTO newPassword
    ) throws UberException {
        var user = userService.getUserById(id);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        userService.resetPassword(user.getEmail(), newPassword);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/login")
    public TokenDTO login(@RequestBody EmailPasswordDTO passwordDTO) throws UberException {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                passwordDTO.getEmail(),
                passwordDTO.getPassword()
        );
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        User user = userService.getUser(passwordDTO.getEmail());
        if (!user.getActive()) {
            throw new UberException(HttpStatus.BAD_REQUEST, "User not activated (check email)!");
        }

        String token = jwtTokenUtil.generateToken(passwordDTO.getEmail(), user.getRole(), user.getId());
        return new TokenDTO(token, null);
    }

    @PutMapping(value = "/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> block(
            @PathVariable("id") Integer id) throws UberException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        if (user.getBlocked()) {
            throw new UberException(HttpStatus.BAD_REQUEST, "User already blocked!");
        }
        user.setBlocked(Boolean.TRUE);
        userRepositoryJPA.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> unblock(
            @PathVariable("id") Integer id) throws UberException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        if (!user.getBlocked()) {
            throw new UberException(HttpStatus.BAD_REQUEST, "User is not blocked!");
        }
        user.setBlocked(Boolean.FALSE);
        userRepositoryJPA.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{id}/note")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Remark> remarkPost(
            @PathVariable("id") Integer id,
            @RequestBody Remark remark) throws UberException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        remark.setUser(user);
        remarkRepositoryJPA.save(remark);
        return new ResponseEntity<>(remark, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/note")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UberPageDTO<Remark>> getRemark(
            @PathVariable("id") Integer id,
            Pageable pageable) throws UberException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        Page<Remark> pages = remarkRepositoryJPA.findByUserId(id, pageable);
        return new ResponseEntity<>(new UberPageDTO<>(pages), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/message")
    public ResponseEntity<SentUserMessageDTO> postMsg(
            @PathVariable("id") Integer id,
            Principal principal,
            @RequestBody CreateUserMessageDTO message) throws UberException {
        Message msg = new Message();
        if (!userRepositoryJPA.existsById(id)) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        if (Objects.equals(message.getType(), "RIDE") && !rideRepositoryJPA.existsById(message.getRideId())) {
            throw new UberException(HttpStatus.NOT_FOUND, "Ride does not exist!");
        }
        var actualUser = userService.getUser(principal.getName());
        msg.setSender(actualUser);
        msg.setReceiver(userRepositoryJPA.getReferenceById(id));
        msg.setRideId(message.getRideId());
        msg.setText(message.getMessage());
        msg.setType(MessageType.valueOf(message.getType()));
        msg.setTime(LocalDateTime.now());
        messageRepositoryJPA.save(msg);
        return new ResponseEntity<>(new SentUserMessageDTO(msg), HttpStatus.OK);
    }


}
