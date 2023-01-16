package iss.tim4.domain.dto.driver;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverRideDTO {

    private Integer id;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Field email format is not valid!")
    private String email;


    public DriverRideDTO(Driver d){
        this.id = d.getId();
        this.email = d.getEmail();
    }
}

