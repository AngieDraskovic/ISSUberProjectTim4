package iss.tim4.domain.dto;

import iss.tim4.domain.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

    @Valid
    private LocationDTO departure;
    @Valid
    private LocationDTO destination;

    public RouteDTO(Route route) {
        this.departure = new LocationDTO(route.getStartLocation());
        this.destination =  new LocationDTO(route.getEndLocation());
    }
}
