package iss.tim4.domain.dto;

import iss.tim4.domain.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private Long id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private String password;
    private boolean blocked;
    private boolean active;
    private VehicleDTO vehicle;

    public DriverDTO(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getName();
        this.surname = driver.getSurname();
        this.email = driver.getEmail();
        this.profilePicture = driver.getProfilePicture();
        this.telephoneNumber = driver.getTelephoneNumber();
        this.address = driver.getAddress();
        this.password = driver.getPassword();
        this.blocked = driver.getBlocked();
        this.active = driver.getActive();
        this.vehicle = new VehicleDTO(driver.getVehicle());
    }

    public void copyValues(DriverDTO driver) {
        this.setName(driver.getName());
    }
}
