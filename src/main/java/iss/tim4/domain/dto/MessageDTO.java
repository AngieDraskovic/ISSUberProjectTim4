package iss.tim4.domain.dto;

import iss.tim4.domain.MessageType;
import iss.tim4.domain.dto.user.UserDTO;
import iss.tim4.domain.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long id;
    private UserDTO sender;
    private UserDTO receiver;
    private String text;
    private LocalDateTime time;
    private MessageType type;
    private Long rideId;

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.sender = new UserDTO(message.getSender());
        this.receiver = new UserDTO(message.getReceiver());
        this.text = message.getText();
        this.time = message.getTime();
        this.type = message.getType();
        this.rideId = message.getRideId();
    }
}
