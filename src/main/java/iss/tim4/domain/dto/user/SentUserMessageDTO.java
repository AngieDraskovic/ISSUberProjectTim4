package iss.tim4.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentUserMessageDTO {
    private Long id;
    @NotNull (message = "Field timeOfSending is required!")
    private String timeOfSending;
    @NotNull (message = "Field senderId is required!")
    private Long senderId;
    @NotNull (message = "Field receiverId is required!")
    private Long receiverId;
    @Size (max = 300, message = "Field message cannot be longer than 300 characters!")
    private String message;
    @NotEmpty (message = "Field type is required!")
    private String type;
    private Long rideId;    // Ponovo voznja moze biti null
}
