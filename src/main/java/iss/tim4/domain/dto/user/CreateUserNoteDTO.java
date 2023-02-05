package iss.tim4.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserNoteDTO {
    @Size (max = 300, message = "Field message cannot be longer than 300 characters!")
    private String message;
}
