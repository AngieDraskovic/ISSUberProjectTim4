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

    public HashMap<Integer, Object> driverRideSurveyThreads = new HashMap<>();
    @Autowired
    private DriverRequestServiceJPA driverRequestServiceJPA;

    public Map<Integer, Integer> driverRideAgreement = new HashMap<>();
    public Map<Integer, Integer> driverRideAgreementTest = new HashMap<>();

    @MessageMapping("/driver-survey/{id}")
    public void driverSurveyAnswer(Integer agree, @DestinationVariable Integer id) throws Exception {
        System.out.println("Driver " + id + " agreed to request with code " + agree);
        driverRideAgreement.put(id, agree);
        synchronized (driverRideSurveyThreads.get(agree)) {
            driverRideSurveyThreads.get(agree).notify();
            driverRideSurveyThreads.remove(agree);
        }
    }

    @MessageMapping("/driver-survey/{id}/decline")
    public void driverSurveyAnswerBad(Integer agree, @DestinationVariable Integer id) throws Exception {
        System.out.println("Driver " + id + " declined request with code " + agree);
        driverRideAgreement.put(id, -1);
        synchronized (driverRideSurveyThreads.get(agree)) {
            driverRideSurveyThreads.get(agree).notify();
            driverRideSurveyThreads.remove(agree);
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
