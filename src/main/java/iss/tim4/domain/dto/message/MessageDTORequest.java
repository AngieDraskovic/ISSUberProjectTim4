package iss.tim4.domain.dto.message;

import iss.tim4.domain.MessageType;
import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTORequest {

    @Valid
    private UserDTO sender;
    @Valid
    private UserDTO receiver;
    @Size(max = 300, message = "Field text cannot be longer than 300 characters!")
    private String text;
    @NotNull(message = "Field time is required!")
    private LocalDateTime time;
    @NotNull (message = "Field type is required!")
    private MessageType type;
    private Integer rideId;    // Ne mora za voznju biti poruka

    public MessageDTORequest(Message message) {
        this.sender = new UserDTO(message.getSender());
        this.receiver = new UserDTO(message.getReceiver());
        this.text = message.getText();
        this.time = message.getTime();
        this.type = message.getType();
        this.rideId = message.getRideId();
    }
}
