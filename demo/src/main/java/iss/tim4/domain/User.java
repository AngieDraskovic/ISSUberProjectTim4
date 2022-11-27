package iss.tim4.domain;

import lombok.Data;

import java.util.Objects;

@Data
public class User {

    private Long id;
    private String name;
    private String surname;
    private String imgPath;
    private String phone;
    private String email;
    private String address;
    private String password;

    public User(String name, String surname, String imgPath, String phone, String email, String address, String password){
        this.name = name;
        this.surname = surname;
        this.imgPath = imgPath;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.password = password;
    }

}
