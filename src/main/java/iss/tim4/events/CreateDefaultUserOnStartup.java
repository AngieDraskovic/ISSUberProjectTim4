package iss.tim4.events;

import iss.tim4.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CreateDefaultUserOnStartup {
    @Autowired
    private EmailServiceImpl emailService;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUser() {
        System.out.println("hello world, I have just started up");

        emailService.sendSimpleMessage("alex.type59@gmail.com", "HEY", "HEY");
    }
}
