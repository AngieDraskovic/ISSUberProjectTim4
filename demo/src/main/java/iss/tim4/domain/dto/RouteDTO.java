package iss.tim4.domain.dto;

import iss.tim4.domain.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

    private Long id;
    private LocationDTO startLocation;
    private LocationDTO endLocation;
    private Double kilometers;

    public RouteDTO(Route route) {
        this.id = route.getId();
        this.startLocation = new LocationDTO(route.getStartLocation());
        this.endLocation = new LocationDTO(route.getEndLocation());
        this.kilometers = route.getKilometers();
    }
}
