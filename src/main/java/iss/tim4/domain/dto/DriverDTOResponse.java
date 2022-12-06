package iss.tim4.domain.dto;

import iss.tim4.domain.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTOResponse {

    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
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
