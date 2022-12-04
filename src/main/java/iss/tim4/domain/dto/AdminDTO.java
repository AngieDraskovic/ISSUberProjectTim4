package iss.tim4.domain.dto;

import iss.tim4.domain.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String imgPath;

    public AdminDTO(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.name = admin.getName();
        this.surname = admin.getSurname();
        this.imgPath = admin.getImgPath();
    }
}
