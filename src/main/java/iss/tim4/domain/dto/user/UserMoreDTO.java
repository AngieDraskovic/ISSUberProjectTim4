package iss.tim4.domain.dto.user;

import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.dto.passenger.PassengerDTO;
import iss.tim4.domain.dto.passenger.PassengerDTOResult;
import iss.tim4.domain.model.DriverRequest;
import iss.tim4.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMoreDTO {
    UserDTO user;
    Role role;

    DriverRequest driver;
    PassengerDTOResult passenger;
}
