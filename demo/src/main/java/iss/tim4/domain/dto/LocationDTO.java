package iss.tim4.domain.dto;

import iss.tim4.domain.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private Long id;
    private String address;
    private Double geoLength;
    private Double geoWidth;

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.address = location.getAddress();
        this.geoLength = location.getGeoLength();
        this.geoWidth = location.getGeoWidth();
    }
}
