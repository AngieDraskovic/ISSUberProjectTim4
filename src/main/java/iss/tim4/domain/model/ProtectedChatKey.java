package iss.tim4.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
public class ProtectedChatKey {
    @Id
    private Integer userId;
    private Integer key;
}
