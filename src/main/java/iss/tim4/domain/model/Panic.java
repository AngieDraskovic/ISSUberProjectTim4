package iss.tim4.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Panic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "panic_time", nullable = false)
    private LocalDateTime time;

    @Column(name = "reason", nullable = false)
    private String reason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",  referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ride_id", referencedColumnName = "id")
    private Ride ride;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Panic panic = (Panic) o;
        return id != null && Objects.equals(id, panic.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
