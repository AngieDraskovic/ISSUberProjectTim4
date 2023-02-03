package iss.tim4.domain.model;

import iss.tim4.domain.MessageType;
import javax.persistence.*;

import iss.tim4.domain.dto.message.MessageDTORequest;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @OneToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "send_time", nullable = false)
    private LocalDateTime time;

    @Column(name = "type", nullable = false)
    private MessageType type;

    @Column(name = "ride_id")
    private Integer rideId;


    public Message(MessageDTORequest messageDTORequest) {
        this.text = messageDTORequest.getText();
        this.time = messageDTORequest.getTime();
        this.type = messageDTORequest.getType();
        this.rideId = messageDTORequest.getRideId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Message message = (Message) o;
        return id != null && Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
