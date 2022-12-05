package iss.tim4.domain.dto;

import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverRideDTO {

    private Long id;
    private String email;
    private String type;

    public DriverRideDTO(Driver d){
        this.id = d.getId();
        this.email = d.getEmail();
        this.type = "VOZAC";
    }
}

