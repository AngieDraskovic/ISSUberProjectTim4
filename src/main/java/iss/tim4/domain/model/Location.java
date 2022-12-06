package iss.tim4.domain.model;

import iss.tim4.domain.dto.LocationDTO;
import jakarta.persistence.*;
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
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "geo_width", nullable = false)
    private Double geoWidth;

    @Column(name = "geo_length", nullable = false)
    private Double geoLength;

    public Location(LocationDTO locationDTO) {
        this.address = locationDTO.getAddress();
        this.geoWidth = locationDTO.getLongitude();
        this.geoLength = locationDTO.getLatitude();
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
        this.geoWidth = locationDTO.getLatitude();
        this.geoLength = locationDTO.getLongitude();
    }
}
