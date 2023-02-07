package iss.tim4.domain.dto;

import iss.tim4.domain.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    @NotEmpty (message = "Field address is required!")
    private String address;
 //   @NotEmpty (message = "Field latitude is required!")
    @Min(value=0)
    private Double latitude;

   // @NotEmpty (message = "Field longitude is required!")
    @Min(value=0)
    private Double longitude;

    public LocationDTO(Location location) {
        this.address = location.getAddress();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
}
