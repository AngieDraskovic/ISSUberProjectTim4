package iss.tim4.domain.model;

import iss.tim4.domain.dto.LocationDTO;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "geo_width", nullable = false)
    private Double latitude;

    @Column(name = "geo_length", nullable = false)
    private Double longitude;

    public Location(LocationDTO locationDTO) {
        this.address = locationDTO.getAddress();
        this.latitude = locationDTO.getLongitude();
        this.longitude = locationDTO.getLatitude();
    }

    public Location(String s, double v, double v1) {
        this.address = s;
        this.geoWidth = v;
        this.geoLength = v1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Location location = (Location) o;
        return id != null && Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void update(LocationDTO locationDTO) {
        this.address = locationDTO.getAddress();
        this.latitude = locationDTO.getLatitude();
        this.longitude = locationDTO.getLongitude();
    }

    public double distanceTo(LocationDTO other) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(other.getLatitude() - this.latitude);
        double dLng = Math.toRadians(other.getLongitude() - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(this.latitude))
                * Math.cos(Math.toRadians(other.getLatitude())) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist;
    }
}
