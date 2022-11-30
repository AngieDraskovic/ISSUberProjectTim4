package iss.tim4.domain.dto;


public class PassengerDTO extends UserDTO {
    public PassengerDTO(Long id, String name, String surname, String imgPath, String phone, String email, String address, String password) {
        super(id, name, surname, imgPath, phone, email, address, password);
    }

    public void copyValues(PassengerDTO passenger) {
        this.setName(passenger.getName());
    }
}
