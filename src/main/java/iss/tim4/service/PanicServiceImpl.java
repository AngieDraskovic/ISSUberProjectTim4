package iss.tim4.service;

import iss.tim4.domain.dto.PanicDTO;
import iss.tim4.domain.dto.UberPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class PanicServiceImpl implements PanicService {
    @Autowired
    private UserService userService;

    @Override
    public Collection<PanicDTO> findAll() {
        return List.of(new PanicDTO(1, null, null, LocalDateTime.now(), "KEKEKEKEKEK"));
    }

    @Override
    public UberPageDTO<PanicDTO> findAll(Pageable pageable) {
        return new UberPageDTO<PanicDTO>(
                1L,
                List.of(new PanicDTO(1, null, null, LocalDateTime.now(), "KEKEKEKEKEK"))
        );
    }
}
