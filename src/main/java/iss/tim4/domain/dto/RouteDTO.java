package iss.tim4.domain.dto;

import iss.tim4.domain.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

    private LocationDTO departure;
    private LocationDTO destination;

    public RouteDTO(Route route) {
        this.departure = new LocationDTO(route.getStartLocation());
        this.destination =  new LocationDTO(route.getEndLocation());
    }
}
