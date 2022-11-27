package iss.tim4.domain;


public class Passenger extends User {
    public Passenger(Long id, String name, String surname, String imgPath, String phone, String email, String address, String password) {
        super(id, name, surname, imgPath, phone, email, address, password);
    }

    public void copyValues(Passenger passenger) {
        this.setName(passenger.getName());
    }
}
