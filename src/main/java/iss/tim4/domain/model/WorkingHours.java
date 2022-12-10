package iss.tim4.domain.model;

import iss.tim4.domain.dto.working.hours.WorkingHoursDTOResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime start;

    @Column(name = "end_time")
    private LocalDateTime end;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    @ToString.Exclude
    private Driver driver;

    public WorkingHours(Driver driver, WorkingHoursDTOResponse workingHoursDTOResponse) {
        this.driver = driver;
        this.start = workingHoursDTOResponse.getStart();
        this.end = workingHoursDTOResponse.getEnd();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkingHours that = (WorkingHours) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void update(WorkingHoursDTOResponse workingHoursDTOResponse) {
        this.start = workingHoursDTOResponse.getStart();
        this.end = workingHoursDTOResponse.getEnd();
    }
}
