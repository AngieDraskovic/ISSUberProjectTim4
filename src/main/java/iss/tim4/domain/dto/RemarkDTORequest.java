package iss.tim4.domain.dto;

import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.model.Remark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemarkDTORequest {


    @Size(min = 5, max = 300, message = "Your remark is too short")
    private String message;
    private LocalDateTime date;

    private Integer userId;

    public RemarkDTORequest(Remark remark) {
        this.message = remark.getMessage();
        this.date = remark.getDate();
        this.userId = remark.getUser().getId();
    }
}
