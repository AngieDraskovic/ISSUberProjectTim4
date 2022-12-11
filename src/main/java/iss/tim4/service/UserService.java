package iss.tim4.service;

import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.dto.user.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UberPageDTO<RideDTOResponse> getRidesOfUser(Pageable pageable, Long userId);

    UberPageDTO<UserDTO> getAllUsers(Pageable pageable);

    TokenDTO login(EmailPasswordDTO userInput);

    UberPageDTO<SentUserMessageDTO> getUserMessages(Pageable pageable, Long userId);

    SentUserMessageDTO createUserMessage(CreateUserMessageDTO createUserMessageDTO, Long userId);

    UberPageDTO<UserNoteDTO> getUserNote(Pageable pageable, Long userId);

    UserNoteDTO createUserNote(CreateUserNoteDTO createUserNoteDTO, Long userId);
}
