package iss.tim4.domain.dto.driver;

import iss.tim4.domain.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTOResult {

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
    private boolean active;
    private boolean blocked;



    public DriverDTOResult(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getName();
        this.surname = driver.getSurname();
        this.email = driver.getEmail();
        this.profilePicture = driver.getProfilePicture();
        this.telephoneNumber = driver.getTelephoneNumber();
        this.address = driver.getAddress();
        this.active = driver.getActive();
        this.blocked = driver.getBlocked();
    }

    public void copyValues(DriverDTOResult driver) {
        this.setName(driver.getName());
    }
}
