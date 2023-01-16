package iss.tim4.domain.dto.passenger;

import iss.tim4.domain.model.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTOUpdate {

    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;



    public PassengerDTOUpdate(Passenger passenger){
        this.name = passenger.getName();
        this.surname = passenger.getSurname();
        this.email = passenger.getEmail();
        this.profilePicture = passenger.getProfilePicture();
        this.telephoneNumber = passenger.getTelephoneNumber();
        this.address = passenger.getAddress();

    }
}
