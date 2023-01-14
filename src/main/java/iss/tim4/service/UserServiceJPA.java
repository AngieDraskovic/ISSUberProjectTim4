package iss.tim4.service;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.ChangePasswordDTO;
import iss.tim4.domain.dto.security.ResetPasswordDTO;
import iss.tim4.domain.dto.user.*;
import iss.tim4.domain.model.Activation;
import iss.tim4.domain.model.Role;
import iss.tim4.domain.model.User;
import iss.tim4.errors.UberException;
import iss.tim4.repository.ActivationRepositoryJPA;
import iss.tim4.repository.PassengerRepositoryJPA;
import iss.tim4.repository.RideRepositoryJPA;
import iss.tim4.repository.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Random;

@Service
public class UserServiceJPA implements UserService {
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;
    @Autowired
    private RideRepositoryJPA rideRepositoryJPA;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private ActivationRepositoryJPA activationRepositoryJPA;
    @Autowired
    private EmailServiceImpl emailService;
    private final Random rand = new Random();

    @Override
    public UberPageDTO<RideDTOResponse> getRidesOfUser(Pageable pageable, Integer userId) {
        User user = userRepositoryJPA.getReferenceById(userId);
        if (user.getRole() == Role.PASSENGER) {
            return new UberPageDTO<>(rideRepositoryJPA.findByPassengersId(pageable, userId).map(RideDTOResponse::new));
        }
        if (user.getRole() == Role.DRIVER) {
            return new UberPageDTO<>(rideRepositoryJPA.findByDriverId(pageable, userId).map(RideDTOResponse::new));
        }

        return new UberPageDTO<>();
    }

    @Override
    public UberPageDTO<UserDTO> getAllUsers(Pageable pageable) {
        return new UberPageDTO<>(userRepositoryJPA.findAll(pageable).map(UserDTO::new));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepositoryJPA.findOneByEmail(username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public User getUser(String username) {
        if (!userRepositoryJPA.existsByEmail(username)) {
            return null;
        }
        return userRepositoryJPA.findOneByEmail(username);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepositoryJPA.getReferenceById(id);
    }

    @Override
    public void changePassword(Integer id, ChangePasswordDTO passwords) throws UberException {
        User user = getUserById(id);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        if (!passwordEncoder.matches(passwords.getOldPassword(), user.getPassword())) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Current password is not matching!");
        }
        user.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
        userRepositoryJPA.save(user);
    }

    @Override
    public void resetPassword(String email, ResetPasswordDTO resetPasswordDTO) throws UberException {
        User user = getUser(email);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        if (!activationRepositoryJPA.existsById(email)) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Code is expired or not correct!");
        }
        if (!Objects.equals(resetPasswordDTO.getCode(), activationRepositoryJPA.getReferenceById(email).getCode())) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Code is expired or not correct!");
        }
        ZoneOffset zdt = ZoneOffset.UTC;
        if (LocalDateTime.now().toInstant(zdt).toEpochMilli() -
                activationRepositoryJPA.getReferenceById(email).getCreated().toInstant(zdt).toEpochMilli()
                > 5 * 60 * 1000) {
            throw new UberException(HttpStatus.BAD_REQUEST, "Code is expired or not correct!");
        }
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepositoryJPA.save(user);
    }

    @Override
    public void resetPassword(String email) throws UberException {
        User user = getUser(email);
        if (user == null) {
            throw new UberException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        if (activationRepositoryJPA.existsById(email)) {
            activationRepositoryJPA.deleteById(email);
        }
        Activation activation = new Activation(email, rand.nextInt(), LocalDateTime.now());
        activationRepositoryJPA.save(activation);

        String message = "Dear, " + user.getName() +
                "!\n\n" + "This is your code for a password reset:\n" + activation.getCode() +
                "\n\nIf you did not perform password reset - contact our support: " +
                "support@easy.go" + "\n\nBest regards,\nEasyGo team!";

        emailService.sendSimpleMessage(email, "Password reset", message);
    }
}
