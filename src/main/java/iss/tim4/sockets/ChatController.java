package iss.tim4.sockets;

import iss.tim4.domain.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDTO send(MessageDTO message) throws Exception {
        return message;
    }

    @MessageMapping("/send/message/admin")
    @SendTo("/topic/admin")
    public String sendMessageToAdmin(String message, Principal principal) {
        // Here you can check the role of the user sending the message
        // and make sure it's a passenger before sending the message to the admin
        // You can also set the sender and receiver information on the message
        // before sending it to the topic
        return message;
    }
}

