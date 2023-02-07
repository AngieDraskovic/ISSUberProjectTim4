package iss.tim4.domain.dto;

import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.model.Panic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PanicDTORequest {
    @NotNull(message = "Field user is required!")
    private Integer userId;
    @NotNull (message = "Field ride is required!")
    private RideDTOResponse ride;
    @NotNull (message = "Field time is required!")
    private LocalDateTime time;
    @Size(min = 5, max = 300, message = "Field reason format is not valid! (2-300)")
    private String reason;

    public PanicDTORequest(Panic panic){
        this.userId = panic.getUser().getId();
        this.ride = new RideDTOResponse(panic.getRide());
        this.time = panic.getTime();
        this.reason = panic.getReason();
    }
}
