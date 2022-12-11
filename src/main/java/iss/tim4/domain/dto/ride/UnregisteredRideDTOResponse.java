package iss.tim4.domain.dto.ride;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnregisteredRideDTOResponse {
    private int estimatedTimeInMinutes;
    private int estimatedCost;
}
