package iss.tim4.domain.dto.passenger;

import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRideDTO {

    private Integer id;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Field email format is not valid!")
    private String email;


    public PassengerRideDTO(Passenger p){
        this.id = p.getId();
        this.email = p.getEmail();

    }
}
