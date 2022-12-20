package iss.tim4.domain.model;

import iss.tim4.domain.MessageType;
import javax.persistence.*;
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
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "type", nullable = false)
    private MessageType type;

    @Column(name = "ride_id")
    private Long rideId;




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
