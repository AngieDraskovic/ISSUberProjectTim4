package iss.tim4.events;

import iss.tim4.domain.model.User;
import iss.tim4.repository.UserRepositoryJPA;
import iss.tim4.security.PasswordEncodingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateDefaultUserOnStartup {
    @Autowired
    UserRepositoryJPA userRepositoryJPA;
    @Autowired
    PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUser() {
        System.out.println("We don't store users passwords, we store only hash codes");
        System.out.println("That's why we need to encode passwords of all predefined users");

        for (User user : userRepositoryJPA.findAll()) {
            System.out.printf("Encode user's %s (%s) password %s%n", user.getEmail(), user.getRole(), user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepositoryJPA.save(user);
        }
    }
}
