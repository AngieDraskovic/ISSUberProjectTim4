package iss.tim4.domain.dto;

import iss.tim4.domain.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private String address;
    private Double latitude;
    private Double longitude;

    public LocationDTO(Location location) {
        this.address = location.getAddress();
        this.latitude = location.getGeoLength();
        this.longitude = location.getGeoWidth();
    }
}
