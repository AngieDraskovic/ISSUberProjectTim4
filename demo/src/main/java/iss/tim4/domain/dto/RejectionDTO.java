package iss.tim4.domain.dto;

import iss.tim4.domain.model.Rejection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectionDTO {

    private Long id;
    private RideDTO ride;
    private String reason;
    private UserDTO user;
    private LocalDateTime time;

    public RejectionDTO(Rejection rejection) {
        this.id = rejection.getId();
        this.ride = new RideDTO(rejection.getRide());
        this.reason = rejection.getReason();
        this.user = new UserDTO(rejection.getUser());
        this.time = rejection.getTime();
    }
}
