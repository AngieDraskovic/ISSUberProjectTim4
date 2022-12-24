package iss.tim4.events;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CreateDefaultUserOnStartup {

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUser() {
        System.out.println("hello world, I have just started up");
    }
}
