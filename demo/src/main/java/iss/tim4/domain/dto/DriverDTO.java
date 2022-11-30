package iss.tim4.domain.dto;

public class DriverDTO extends UserDTO {

    public DriverDTO(Long id, String name, String surname, String imgPath, String phone, String email, String address, String password) {
        super(id , name, surname, imgPath, phone, email, address, password);
    }

    public void copyValues(DriverDTO driver) {
        this.setName(driver.getName());
    }
}
