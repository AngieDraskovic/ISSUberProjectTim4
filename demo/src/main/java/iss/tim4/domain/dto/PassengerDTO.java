package iss.tim4.domain.dto;

import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    private Long id;
    private String name;
    private String surname;
    private String imgPath;
    private String phone;
    private String email;
    private String address;
    private String password;
    private boolean blocked;
    private boolean active;

    public PassengerDTO(Passenger passenger){
        this.id = passenger.getId();
        this.name = passenger.getName();
        this.surname = passenger.getSurname();
        this.email = passenger.getEmail();
        this.imgPath = passenger.getImgPath();
        this.phone = passenger.getPhone();
        this.address = passenger.getAddress();
        this.password = passenger.getPassword();
        this.blocked = passenger.getBlocked();
        this.active = passenger.getActive();
    }

    public void copyValues(PassengerDTO passenger) {
        this.setName(passenger.getName());
    }
}
