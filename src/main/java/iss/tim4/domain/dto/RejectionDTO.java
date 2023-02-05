package iss.tim4.domain.dto;

import iss.tim4.domain.model.Rejection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectionDTO {

    @NotEmpty (message = "Field reason is required!")
    private String reason;
    @NotNull(message = "Field timeOfRejection is required!")
    private LocalDateTime timeOfRejection;

    public RejectionDTO(Rejection rejection) {
        if (rejection == null)
            return;
        this.reason = rejection.getReason();
        this.timeOfRejection = rejection.getTime();
    }

}
