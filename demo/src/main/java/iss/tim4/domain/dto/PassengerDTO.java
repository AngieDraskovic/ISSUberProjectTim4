package iss.tim4.domain.dto;

import iss.tim4.domain.model.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private String password;
    private boolean blocked;        // TODO: da li izbaciti ova dva atributa
    private boolean active;

    public PassengerDTO(Passenger passenger){
        this.name = passenger.getName();
        this.surname = passenger.getSurname();
        this.email = passenger.getEmail();
        this.profilePicture = passenger.getProfilePicture();
        this.telephoneNumber = passenger.getTelephoneNumber();
        this.address = passenger.getAddress();
        this.password = passenger.getPassword();
        this.blocked = passenger.getBlocked();
        this.active = passenger.getActive();
    }

    public void copyValues(PassengerDTO passenger) {
        this.setName(passenger.getName());
    }
}
