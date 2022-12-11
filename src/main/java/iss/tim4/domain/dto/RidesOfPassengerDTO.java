package iss.tim4.domain.dto;

import iss.tim4.domain.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RidesOfPassengerDTO {

    private int totalCount;
    private OneRideOfPassengerDTO[] results;

    public RidesOfPassengerDTO(OneRideOfPassengerDTO[] results, int totalCount){
        this.totalCount = totalCount;
        this.results = results;
    }

}
