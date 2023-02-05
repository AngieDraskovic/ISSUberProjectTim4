package iss.tim4.domain.dto.ride;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnregisteredRideDTOResponse {
    @NotNull (message = "Field estimatedTimeInMinutes is required!")
    private int estimatedTimeInMinutes;
    @NotNull (message = "Field estimatedCost is required!")
    private int estimatedCost;
}
