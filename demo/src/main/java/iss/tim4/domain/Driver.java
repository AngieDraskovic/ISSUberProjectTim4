package iss.tim4.domain;

public class Driver extends User {

    public Driver(Long id, String name, String surname, String imgPath, String phone, String email, String address, String password) {
        super(id , name, surname, imgPath, phone, email, address, password);
    }

    public void copyValues(Driver driver) {
        this.setName(driver.getName());
    }
}
