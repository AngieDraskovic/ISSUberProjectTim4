package iss.tim4.domain.dto.user;

import iss.tim4.domain.model.Role;
import iss.tim4.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    @NotEmpty(message = "Field name is required!")
    private String name;
    @NotEmpty (message = "Field surname is required!")
    private String surname;
    private String profilePicture;  // Slika nije obavezna
    @Size(min = 6, max = 30, message = "Field telephoneNumber format is not valid!")
    private String telephoneNumber;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Field email format is not valid!")
    private String email;
    @NotEmpty(message = "Field address is required!")
    private String address;
    private boolean blocked;
    private Role role;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.profilePicture = user.getProfilePicture();
        this.telephoneNumber = user.getTelephoneNumber();
        this.address = user.getAddress();
        this.blocked = user.getBlocked();
        this.role = user.getRole();
    }
}
