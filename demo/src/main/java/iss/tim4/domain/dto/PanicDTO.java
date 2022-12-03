package iss.tim4.domain.dto;

import iss.tim4.domain.model.Panic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PanicDTO {

    private Long id;
    private UserDTO user;
    private RideDTO ride;
    private LocalDateTime time;
    private String reason;

    public PanicDTO(Panic panic){
        this.id = panic.getId();
        this.user = new UserDTO(panic.getUser());
        this.ride = new RideDTO(panic.getRide());
        this.time = panic.getTime();
        this.reason = panic.getReason();
    }
}
