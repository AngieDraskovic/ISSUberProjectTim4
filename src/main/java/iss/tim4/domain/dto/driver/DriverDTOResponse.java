package iss.tim4.domain.dto.driver;

import iss.tim4.domain.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTOResponse {

    @NotEmpty (message = "Field name is required!")
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

    public DriverDTOResponse(Driver driver) {
        this.name = driver.getName();
        this.surname = driver.getSurname();
        this.email = driver.getEmail();
        this.profilePicture = driver.getProfilePicture();
        this.telephoneNumber = driver.getTelephoneNumber();
        this.address = driver.getAddress();
        this.password = driver.getPassword();
    }

    public void copyValues(DriverDTOResult driver) {
        this.setName(driver.getName());
    }
}
