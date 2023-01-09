package iss.tim4.service;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.user.*;
import iss.tim4.domain.model.Role;
import iss.tim4.domain.model.User;
import iss.tim4.repository.RideRepositoryJPA;
import iss.tim4.repository.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJPA implements UserService {
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;
    @Autowired
    private RideRepositoryJPA rideRepositoryJPA;

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
        return userRepositoryJPA.findOneByEmail(username);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepositoryJPA.getReferenceById(id);
    }

    @Override
    public User getUserByTelephoneNumber(String telephoneNumber){return userRepositoryJPA.findOnByTelephoneNumber(telephoneNumber);}
}
