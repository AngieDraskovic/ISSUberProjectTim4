package iss.tim4.service;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.dto.user.*;
import iss.tim4.domain.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    UberPageDTO<RideDTOResponse> getRidesOfUser(Pageable pageable, Integer userId);

    UberPageDTO<UserDTO> getAllUsers(Pageable pageable);

    UserDetails loadUserByUsername(String username);

    User getUser(String username);

    User getUserById(Integer id);

    User getUserByTelephoneNumber(String telephoneNumber);
}
