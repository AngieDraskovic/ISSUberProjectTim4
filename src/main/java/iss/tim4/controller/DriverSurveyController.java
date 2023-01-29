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

    public Map<Driver, Integer> driverRideAgreement = new HashMap<>();

    @MessageMapping("/driver-survey")
    @SendTo("/topic/driver-request")
    public DriverRequestDTOResult sendSpecific(String msg) throws Exception {
        System.out.println(msg);
        return new DriverRequestDTOResult(driverRequestServiceJPA.findOne(0));
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
