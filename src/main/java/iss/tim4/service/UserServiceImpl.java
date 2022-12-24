package iss.tim4.service;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.dto.user.*;
import iss.tim4.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RideServiceJPA rideServiceJPA;

    private UserDTO mockUser() {
        return new UserDTO(1, "Sasha", "P", "1", "3", "3", "3");
    }

    private SentUserMessageDTO mockMessage() {
        return new SentUserMessageDTO(123L, "2022-11-25T17:32:28Z", 123L, 123L,
                "The driver is going on a longer route on purpose", "RIDE", 123L);
    }

    private UserNoteDTO mockNote() {
        return new UserNoteDTO(123L, "2022-11-25T17:32:28Z", "The driver is going on a longer route on purpose");
    }

    @Override
    public UberPageDTO<RideDTOResponse> getRidesOfUser(Pageable pageable, Long userId) {
        return new UberPageDTO<>(rideServiceJPA.findAll(pageable).map(RideDTOResponse::new));
    }

    @Override
    public UberPageDTO<UserDTO> getAllUsers(Pageable pageable) {
        return new UberPageDTO<>(243L, List.of(mockUser()));
    }

    @Override
    public TokenDTO login(EmailPasswordDTO userInput) {
        return new TokenDTO("1", "2");
    }

    @Override
    public UberPageDTO<SentUserMessageDTO> getUserMessages(Pageable pageable, Long userId) {
        return new UberPageDTO<>(321L, List.of(mockMessage()));
    }

    @Override
    public SentUserMessageDTO createUserMessage(CreateUserMessageDTO createUserMessageDTO, Long userId) {
        return mockMessage();
    }

    @Override
    public UberPageDTO<UserNoteDTO> getUserNote(Pageable pageable, Long userId) {
        return new UberPageDTO<>(321L, List.of(mockNote()));
    }

    @Override
    public UserNoteDTO createUserNote(CreateUserNoteDTO createUserNoteDTO, Long userId) {
        return mockNote();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("admin")
                .roles(String.valueOf(Role.ADMIN))
                .build();
    }
}
