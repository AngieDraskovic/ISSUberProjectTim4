package iss.tim4.domain.dto.passenger;

import iss.tim4.domain.model.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTOUpdate {

    @NotEmpty(message = "Field name cannnot be empty!")
    private String name;
    @NotEmpty(message = "Field surname cannnot be empty!")
    private String surname;
    private String profilePicture;
    @Size(min = 6, max = 30, message = "Field telephoneNumber format is not valid!")
    private String telephoneNumber;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Field email format is not valid!")
    private String email;
    @NotEmpty(message = "Field address cannot be empty!")
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
