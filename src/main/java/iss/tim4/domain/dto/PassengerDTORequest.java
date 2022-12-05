package iss.tim4.domain.dto;

import iss.tim4.domain.model.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTORequest {

    private int totalCount;
    private PassengerDTOResult[] results;

    public PassengerDTORequest(PassengerDTOResult[] results, int totalCount){
        this.totalCount = totalCount;
        this.results = results;
    }
}
