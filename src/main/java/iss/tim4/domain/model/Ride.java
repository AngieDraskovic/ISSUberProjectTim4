package iss.tim4.domain.model;

import iss.tim4.domain.RideStatus;
import javax.persistence.*;

import iss.tim4.domain.dto.ride.RideDTOExample;
import iss.tim4.domain.dto.ride.RideDTORequest;
import iss.tim4.service.PassengerServiceJPA;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

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
    private Integer id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")      // Ako voznja nije zavrsena, endTime je null
    private LocalDateTime endTime;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @Column(name = "estimated_time_in_minutes", nullable = false)
    private Double estimatedTimeInMinutes;

    @Column(name = "kilometers", nullable = false)
    private Double kilometers;

    @Column(name = "status", nullable = false)
    private RideStatus status;

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
    @JoinColumn(name = "panic_id", referencedColumnName = "id")
    private Panic panic;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_type", referencedColumnName = "id")
    private VehicleType vehicleType;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "favourite_id", referencedColumnName = "id")
//    private FavouriteRoute favouriteRoute;

    public Ride(RideDTORequest rideDTO) {
        this.startTime = rideDTO.getStartTime();
        this.endTime = null;
        this.estimatedTimeInMinutes = rideDTO.getEstimatedTime();
        this.kilometers = rideDTO.getKilometers();
        this.status = RideStatus.ACCEPTED;
        this.babyTransport = rideDTO.getBabyTransport();
        this.petTransport = rideDTO.getPetTransport();
    }


    // for testing :)
    public Ride(LocalDateTime start, LocalDateTime end, Double price, Double minutes, Double kilometers,
                RideStatus status, boolean pets, boolean baby){
        this.startTime = start;
        this.endTime = end;
        this.status = status;
        this.babyTransport = baby;
        this.petTransport = pets;
        this.totalCost = price;
        this.estimatedTimeInMinutes = minutes;
        this.kilometers = kilometers;

    }
    public Ride(Integer id, LocalDateTime start, LocalDateTime end, Double price, Double minutes, Double kilometers,
                RideStatus status, boolean pets, boolean baby){
        this.id = id;
        this.startTime = start;
        this.endTime = end;
        this.status = status;
        this.babyTransport = baby;
        this.petTransport = pets;
        this.totalCost = price;
        this.estimatedTimeInMinutes = minutes;
        this.kilometers = kilometers;

    }

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

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
        for (Route route : routes)
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
