package iss.tim4.domain.dto;

import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRideDTO {

    private Integer id;
    private String email;


    public PassengerRideDTO(Passenger p){
        this.id = p.getId();
        this.email = p.getEmail();

    }
}
