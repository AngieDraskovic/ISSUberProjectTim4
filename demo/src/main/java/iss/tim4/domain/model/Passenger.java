package iss.tim4.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "img_path")  // nullable=true (default value)
    private String imgPath;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    /* I nema neke velike potrebe da se u bazi cuva da li je korisnik trenutno aktivan, ali
       nek stoji da ne razmisljamo o tome, nek su svi atributi u bazi.
     */
    @Column(name = "active", nullable = false)
    private Boolean active;

    /* Naziv tabele je participation jer putnici ucestvuju u voznji, a glupo bi bilo i ordering jer ne mora putnik
    *  da poruci voznju da bi ucestvovao u njoj, moze jedan putnik da poruci za vise njih. */
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "participation", joinColumns = @JoinColumn(name = "ride_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<Ride> rides = new HashSet<Ride>();

    /* Unidirekciona veza putnika i njegovih omiljenih ruta. U tabeli FavouriteRoute se cuva id putnika. */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id")
    @ToString.Exclude
    private Set<Route> favouriteRoutes = new HashSet<Route>();

    public void addRide(Ride ride) {
        rides.add(ride);
        ride.getPassengers().add(this);
    }

    public void addFavouriteRoute(Route route){
        favouriteRoutes.add(route);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Passenger that = (Passenger) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
