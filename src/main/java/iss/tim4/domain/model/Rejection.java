package iss.tim4.domain.model;

import javax.persistence.*;

import iss.tim4.domain.dto.RejectionDTO;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Rejection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "rejection")
    @ToString.Exclude
    private Ride ride;

    @Column(name = "reason", nullable = false)
    private String reason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "rejection_time", nullable = false)
    private LocalDateTime time;

    public Rejection(RejectionDTO rejectionDTO) {
        this.reason = rejectionDTO.getReason();
        this.time = rejectionDTO.getTimeOfRejection();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Rejection rejection = (Rejection) o;
        return id != null && Objects.equals(id, rejection.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
