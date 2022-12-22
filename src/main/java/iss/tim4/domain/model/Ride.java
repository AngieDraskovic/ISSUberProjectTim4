package iss.tim4.domain.model;

import iss.tim4.domain.RideStatus;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @Column(name = "estimated_time_in_minutes", nullable = false)
    private Double estimatedTimeInMinutes;

    @Column(name = "status", nullable = false)
    private RideStatus status;

    @Column(name = "panic", nullable = false)
    private Boolean panic;

    @Column(name = "babies", nullable = false)
    private Boolean babyTransport;

    @Column(name = "pets", nullable = false)
    private Boolean petTransport;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "participation", joinColumns = @JoinColumn(name = "ride_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<Passenger> passengers = new HashSet<Passenger>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    private Driver driver;

    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Route> routes = new HashSet<Route>();

    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Review> reviews = new HashSet<Review>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rejection_id", referencedColumnName = "id")
    private Rejection rejection;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_type", referencedColumnName = "id")
    private VehicleType vehicleType;


    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        passenger.getRides().add(this);
    }

    public void addReview(Review review){
        reviews.add(review);
        review.setRide(this);
    }

    public void addRoute(Route route){
        routes.add(route);
        route.setRide(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ride ride = (Ride) o;
        return id != null && Objects.equals(id, ride.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
