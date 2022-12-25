package iss.tim4.service;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.dto.user.*;
import iss.tim4.domain.model.User;
import iss.tim4.repository.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJPA {
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    public User findOne(Integer id) {
        return userRepositoryJPA.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepositoryJPA.save(user);
    }

}
