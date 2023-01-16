package iss.tim4.domain.model;

import javax.persistence.*;

import iss.tim4.domain.dto.passenger.PassengerRideDTO;
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
@DiscriminatorValue("PASSENGER")
public class Passenger extends User {

    /* Naziv tabele je participation jer putnici ucestvuju u voznji, a glupo bi bilo i ordering jer ne mora putnik
    *  da poruci voznju da bi ucestvovao u njoj, moze jedan putnik da poruci za vise njih. */
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "participation", joinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "ride_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<Ride> rides = new HashSet<Ride>();

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "favourite_route_passenger", joinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "favourite_route_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<FavouriteRoute> favouriteRoutes = new HashSet<FavouriteRoute>();


    /* Unidirekciona veza putnika i njegovih omiljenih ruta. U tabeli FavouriteRoute se cuva id putnika.    TODO:promjeniti da bude klasa FavouriteRoute
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id")
    @ToString.Exclude
    private Set<Route> favouriteRoutes = new HashSet<Route>();
     public void addFavouriteRoute(Route route){
        favouriteRoutes.add(route);
    }
     */
    public void addRide(Ride ride) {
        rides.add(ride);
        ride.getPassengers().add(this);
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
