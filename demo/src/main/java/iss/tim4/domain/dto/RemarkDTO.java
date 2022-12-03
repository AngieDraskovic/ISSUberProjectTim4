package iss.tim4.domain.dto;

import iss.tim4.domain.model.Remark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemarkDTO {

    private Long id;
    private String message;
    private UserDTO user;

    public RemarkDTO(Remark remark) {
        this.id = remark.getId();
        this.message = remark.getMessage();
        this.user = new UserDTO(remark.getUser());
    }
}
