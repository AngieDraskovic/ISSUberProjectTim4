package iss.tim4.domain.dto.passenger;

import iss.tim4.domain.dto.UberPageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTOGetAll {

    private int totalCount;
    private Page<PassengerDTOResult> results;

    public PassengerDTOGetAll( Page<PassengerDTOResult> results, int totalCount){
        this.totalCount = totalCount;
        this.results = results;
    }
}
