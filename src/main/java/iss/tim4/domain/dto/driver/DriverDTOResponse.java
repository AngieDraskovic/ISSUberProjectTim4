package iss.tim4.domain.dto.driver;

import iss.tim4.domain.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTOResponse {

    @NotEmpty (message = "Name cannot be empty")
    private String name;
    @NotEmpty
    private String surname;
    private String profilePicture;  // Slika nije obavezna
    @Size(min = 6, max = 30)
    private String telephoneNumber;
    private String email;
    @NotEmpty
    private String address;
    @NotEmpty
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
