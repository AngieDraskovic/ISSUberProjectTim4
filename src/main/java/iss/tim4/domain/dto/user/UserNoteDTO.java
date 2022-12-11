package iss.tim4.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNoteDTO {
    private Long id;
    private String date;
    private String message;
}
