package iss.tim4.service;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.ChangePasswordDTO;
import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.ResetPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.dto.user.*;
import iss.tim4.domain.model.User;
import iss.tim4.errors.UberException;
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

    void changePassword(Integer id, ChangePasswordDTO passwords) throws UberException;

    void resetPassword(String email, ResetPasswordDTO resetPasswordDTO) throws UberException;

    void resetPassword(String email) throws UberException;

}
