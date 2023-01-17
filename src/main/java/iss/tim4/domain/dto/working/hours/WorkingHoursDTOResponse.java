package iss.tim4.domain.dto.working.hours;

import iss.tim4.domain.model.WorkingHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursDTOResponse {

    @NotNull (message = "Field start is required!")
    private LocalDateTime start;
    private LocalDateTime end;


    public WorkingHoursDTOResponse(WorkingHours workingHours) {
        this.start = workingHours.getStart();
        this.end = workingHours.getEnd();
    }
}
