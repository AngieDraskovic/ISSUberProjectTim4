package iss.tim4.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
public class Activation {
    @Id
    private String userEmail;
    private Integer code;
    private LocalDateTime created;
}
