package iss.tim4.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class PassengerActivation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activationId;

    @Column(name = "passenger_id", nullable = false)
    private int passenger_id;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "life_length", nullable = false)
    private double lifeLength;

}
