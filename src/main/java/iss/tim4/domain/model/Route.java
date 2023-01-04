package iss.tim4.domain.model;

import javax.persistence.*;

import iss.tim4.domain.dto.RouteDTO;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_location_id", referencedColumnName = "id")
    private Location startLocation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_location_id", referencedColumnName = "id")
    private Location endLocation;

    @Column(name = "kilometers", nullable = false)
    private Double kilometers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id")
    private Ride ride;

    public Route(RouteDTO routeDTO, double kilometers) {
        this.startLocation = new Location(routeDTO.getDeparture());
        this.endLocation = new Location(routeDTO.getDestination());
        this.kilometers = kilometers;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Route route = (Route) o;
        return id != null && Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
