package iss.tim4.domain.dto.driver;

import iss.tim4.domain.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTOResult {

    private Integer id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;


    public DriverDTOResult(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getName();
        this.surname = driver.getSurname();
        this.email = driver.getEmail();
        this.profilePicture = driver.getProfilePicture();
        this.telephoneNumber = driver.getTelephoneNumber();
        this.address = driver.getAddress();
    }

    public void copyValues(DriverDTOResult driver) {
        this.setName(driver.getName());
    }
}
