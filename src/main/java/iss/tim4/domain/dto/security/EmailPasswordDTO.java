package iss.tim4.domain.dto.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailPasswordDTO {
    private String email;
    private String password;
}
