package iss.tim4.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReasonDTO {

    @Size(max = 100, message = "Field reason cannot be longer than 100 characters!")
    private String reason;

}
