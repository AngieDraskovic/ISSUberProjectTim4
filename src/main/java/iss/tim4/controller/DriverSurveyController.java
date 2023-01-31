package iss.tim4.controller;

import iss.tim4.domain.dto.driver.request.DriverRequestDTOResult;
import iss.tim4.domain.model.Driver;
import iss.tim4.service.DriverDocumentServiceJPA;
import iss.tim4.service.DriverRequestServiceJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.xml.stream.util.StreamReaderDelegate;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DriverSurveyController {

    @Autowired
    private DriverRequestServiceJPA driverRequestServiceJPA;

    public Map<Integer, Integer> driverRideAgreement = new HashMap<>();

    @MessageMapping("/driver-survey/{id}/{agree}")
    public void driverSurveyAnswer(String msg, @DestinationVariable Integer id, @DestinationVariable Integer agree) throws Exception {
        if (msg.equals("ok")) {
            driverRideAgreement.put(id, agree);
        }
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String send(String message) throws Exception {
        return message + "!";
    }

    @MessageMapping("/chat/{id}")
    @SendTo("/topic/messages")
    public String send(String message, @DestinationVariable Integer id) throws Exception {
        return message + id + "!";
    }
}
