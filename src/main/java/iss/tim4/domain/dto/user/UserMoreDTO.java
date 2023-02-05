package iss.tim4.domain.dto.user;

import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.dto.passenger.PassengerDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.model.DriverRequest;
import iss.tim4.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMoreDTO {
    @NotNull (message = "Field user is required!")
    UserDTO user;
    @NotNull (message = "Field role is required!")
    Role role;

    DriverRequest driver;       // Ovo mi se ne cini dobro
    PassengerDTOResult passenger;   // Ovo okej
}
