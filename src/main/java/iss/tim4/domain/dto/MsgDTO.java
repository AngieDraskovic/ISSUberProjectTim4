package iss.tim4.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor

public class MsgDTO {

    private String message;

    public MsgDTO(String message){
        this.message = message;
    }
}
