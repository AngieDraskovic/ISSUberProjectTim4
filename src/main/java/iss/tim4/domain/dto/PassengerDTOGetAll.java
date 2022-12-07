package iss.tim4.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTOGetAll {

    private int totalCount;
    private PassengerDTOResult[] results;

    public PassengerDTOGetAll(PassengerDTOResult[] results, int totalCount){
        this.totalCount = totalCount;
        this.results = results;
    }
}
