package iss.tim4.domain.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iss.tim4.domain.dto.RemarkDTORequest;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Remark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @JsonIgnore
    private User user;

    public Remark(RemarkDTORequest remarkDTORequest) {
        this.message = remarkDTORequest.getMessage();
        this.date = remarkDTORequest.getDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Remark remark = (Remark) o;
        return id != null && Objects.equals(id, remark.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
