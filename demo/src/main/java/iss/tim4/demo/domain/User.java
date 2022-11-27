package iss.tim4.demo.domain;

import java.util.Objects;

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

    public User(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getName(), user.getName()) && Objects.equals(getSurname(), user.getSurname()) && Objects.equals(getImgPath(), user.getImgPath()) && Objects.equals(getPhone(), user.getPhone()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getAddress(), user.getAddress()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getImgPath(), getPhone(), getEmail(), getAddress(), getPassword());
    }
}
