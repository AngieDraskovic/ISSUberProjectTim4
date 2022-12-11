package iss.tim4.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentUserMessageDTO {
    private Long id;
    private String timeOfSending;
    private Long senderId;
    private Long receiverId;
    private String message;
    private String type;
    private Long rideId;
}
