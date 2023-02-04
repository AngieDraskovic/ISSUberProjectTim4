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
public class PassengerDTOPost {

    private Integer id;
    @NotEmpty(message = "Field name is required!")
    private String name;
    @NotEmpty (message = "Field surname is required!")
    private String surname;
    private String profilePicture;  // Slika nije obavezna
    @Size(min = 6, max = 30, message = "Field telephoneNumber format is not valid!")
    private String telephoneNumber;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Field email format is not valid!")
    private String email;
    @NotEmpty(message = "Field address is required!")
    private String address;
    @NotEmpty(message = "Field password is required!")
    private String password;
    //private boolean blocked;
   // private boolean active;

    @NotEmpty(message = "Field for confirming password is required! ")
    private String confirmPassword;

    public PassengerDTOPost(Passenger passenger){
        this.id = passenger.getId();
        this.name = passenger.getName();
        this.surname = passenger.getSurname();
        this.email = passenger.getEmail();
        this.profilePicture = passenger.getProfilePicture();
        this.telephoneNumber = passenger.getTelephoneNumber();
        this.address = passenger.getAddress();
        this.password = passenger.getPassword();
        this.confirmPassword = passenger.getPassword();
        // this.blocked = passenger.getBlocked();
        // this.active = passenger.getActive();
    }

    public void copyValues(PassengerDTOPost passenger) {
        this.setName(passenger.getName());
    }
}
