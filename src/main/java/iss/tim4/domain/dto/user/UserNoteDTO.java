package iss.tim4.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNoteDTO {
    private Long id;
    @NotNull (message = "Field date is required!")
    private String date;
    @NotNull (message = "Field message is required!")   // Ne znam da li ovo treba da se validira
    private String message;
}
