package iss.tim4.domain.model;

import javax.persistence.*;


import lombok.*;

import java.time.LocalDateTime;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
public class PassengerActivation {
    @Id
    @Column(name = "activation_id", nullable = false)
    private Integer activationId;

    @OneToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;



    @Override
    public int hashCode() {
        return Objects.hash(getActivationId(), getPassenger(), getCreationDate());
    }
}
