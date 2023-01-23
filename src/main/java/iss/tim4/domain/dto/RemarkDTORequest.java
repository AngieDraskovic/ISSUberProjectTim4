package iss.tim4.domain.dto;

import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.model.Remark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemarkDTORequest {

    private String message;
    private LocalDateTime date;

    private Integer userId;

    public RemarkDTORequest(Remark remark) {
        this.message = remark.getMessage();
        this.date = remark.getDate();
        this.userId = remark.getUser().getId();
    }
}
