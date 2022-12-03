package iss.tim4.domain.dto;

import iss.tim4.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String imgPath;
    private String phone;
    private String email;
    private String address;
    private String password;
    private boolean blocked;
    private boolean active;

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.imgPath = user.getImgPath();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.password = user.getPassword();
        this.blocked = user.getBlocked();
        this.active = user.getActive();
    }


}
