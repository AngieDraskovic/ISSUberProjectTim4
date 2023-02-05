package iss.tim4.domain.dto;

import iss.tim4.domain.dto.ride.RideDTOResponse;
import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.model.Panic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PanicDTO {

    private Integer id;
    @NotNull (message = "Field user is required!")
    private UserDTO user;
    @NotNull (message = "Field ride is required!")
    private RideDTOResponse ride;
    @NotNull (message = "Field message is required!")
    private LocalDateTime time;
    @NotEmpty (message = "Field reason is required!")
    private String reason;

    public PanicDTO(Panic panic){
        this.id = panic.getId();
        this.user = new UserDTO(panic.getUser());
        this.ride = new RideDTOResponse(panic.getRide());
        this.time = panic.getTime();
        this.reason = panic.getReason();
    }
}
