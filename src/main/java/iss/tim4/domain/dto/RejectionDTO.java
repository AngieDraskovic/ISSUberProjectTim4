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

    private String reasonOfRejection;
    private LocalDateTime timeOfRejection;

    public RejectionDTO(Rejection rejection) {
        this.reasonOfRejection = rejection.getReason();
        this.timeOfRejection = rejection.getTime();
    }

}
