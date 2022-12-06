package iss.tim4.domain.dto;

import iss.tim4.domain.model.WorkingHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursDTOResult {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;


    public WorkingHoursDTOResult(WorkingHours workingHours) {
        this.id = workingHours.getId();
        this.start = workingHours.getStart();
        this.end = workingHours.getEnd();
    }
}
