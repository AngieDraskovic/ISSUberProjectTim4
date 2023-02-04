package iss.tim4.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOriginPatterns("*");
        registry.addEndpoint("/chat").setAllowedOriginPatterns("*").withSockJS();

        registry.addEndpoint("/driver-survey").setAllowedOriginPatterns("*");
        registry.addEndpoint("/driver-survey").setAllowedOriginPatterns("*").withSockJS();

        registry.addEndpoint("/panic").setAllowedOriginPatterns("*");
        registry.addEndpoint("/panic").setAllowedOriginPatterns("*").withSockJS();
    }
}
