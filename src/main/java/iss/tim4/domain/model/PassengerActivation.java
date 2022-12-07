package iss.tim4.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class PassengerActivation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "life_length", nullable = false)
    private double lifeLength;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PassengerActivation that)) return false;
        return Double.compare(that.getLifeLength(), getLifeLength()) == 0 && Objects.equals(getActivationId(), that.getActivationId()) && Objects.equals(getPassenger(), that.getPassenger()) && Objects.equals(getCreationDate(), that.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActivationId(), getPassenger(), getCreationDate(), getLifeLength());
    }
}
