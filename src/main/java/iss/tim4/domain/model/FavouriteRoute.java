package iss.tim4.domain.model;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.RouteDTO;
import iss.tim4.domain.dto.favourite.route.FavouriteRouteDTORequest;
import iss.tim4.domain.dto.passenger.PassengerRideDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class FavouriteRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "favourite_name", nullable = false)
    private String favouriteName;

    @Column(name = "scheduled_time")    // moze biti null (za pocetak, mozda i promijenimo)
    private LocalDateTime scheduledTime;

    @Column(name = "vehicle_type", nullable = false)
    private VehicleName vehicleType;

    @Column(name = "babies", nullable = false)
    private Boolean babyTransport;

    @Column(name = "pets", nullable = false)
    private Boolean petTransport;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "favourite_route_route", joinColumns = @JoinColumn(name = "favourite_route_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<Route> locations = new HashSet<Route>();

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "favourite_route_passenger", joinColumns = @JoinColumn(name = "favourite_route_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<Passenger> passengers = new HashSet<Passenger>();

    public FavouriteRoute(FavouriteRouteDTORequest favouriteRouteDTORequest) {
        this.favouriteName = favouriteRouteDTORequest.getFavoriteName();
        this.scheduledTime = LocalDateTime.now();
        this.vehicleType = favouriteRouteDTORequest.getVehicleType();
        this.babyTransport = favouriteRouteDTORequest.isBabyTransport();
        this.petTransport = favouriteRouteDTORequest.isPetTransport();
    }



}
