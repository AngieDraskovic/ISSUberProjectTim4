package iss.tim4.domain.dto;

import iss.tim4.domain.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

    private Integer id;
    @NotEmpty (message = "Field username is required!")
    private String username;
    @NotEmpty (message = "Field password is required!")
    private String password;
    @NotEmpty (message = "Field name is required!")
    private String name;
    @NotEmpty (message = "Field surname is required!")
    private String surname;
    private String imgPath;     // Opet slika moze biti null

    public AdminDTO(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getEmail();
        this.password = admin.getPassword();
        this.name = admin.getName();
        this.surname = admin.getSurname();
        this.imgPath = admin.getProfilePicture();
    }
}
