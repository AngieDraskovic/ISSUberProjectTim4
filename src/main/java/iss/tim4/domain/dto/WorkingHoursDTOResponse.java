package iss.tim4.domain.dto;

import iss.tim4.domain.model.WorkingHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursDTOResponse {

    private LocalDateTime start;
    private LocalDateTime end;


    public WorkingHoursDTOResponse(WorkingHours workingHours) {
        this.start = workingHours.getStart();
        this.end = workingHours.getEnd();
    }
}
