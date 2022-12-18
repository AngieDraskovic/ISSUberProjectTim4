package iss.tim4.domain.dto.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {
    private List<PassengerDTOResult> results;

    public PassengerDTO(List<PassengerDTOResult> results, int totalCount){
        this.results = results;
    }
}
