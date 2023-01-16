package iss.tim4.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class MsgDTO {

    private String message;

    public MsgDTO(String message){
        this.message = message;
    }
}
