package iss.tim4.domain.dto.user;

import iss.tim4.domain.model.Message;
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
    private Integer id;
    @NotNull (message = "Field timeOfSending is required!")
    private String timeOfSending;
    @NotNull (message = "Field senderId is required!")
    private Integer senderId;
    @NotNull (message = "Field receiverId is required!")
    private Integer receiverId;
    @Size (max = 300, message = "Field message cannot be longer than 300 characters!")
    private String message;
    @NotEmpty (message = "Field type is required!")
    private String type;
    private Integer rideId;    // Ponovo voznja moze biti null

    public SentUserMessageDTO(Message message) {
        id = message.getId();
        timeOfSending = message.getTime().toString();
        senderId = message.getSender().getId();
        receiverId = message.getReceiver().getId();
        this.message = message.getText();
        type = message.getType().toString();
        rideId = message.getRideId();
    }
}
