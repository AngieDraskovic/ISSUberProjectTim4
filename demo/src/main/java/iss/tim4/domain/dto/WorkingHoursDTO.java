package iss.tim4.domain.dto;

import iss.tim4.domain.model.WorkingHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursDTO {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private DriverDTO driver;

    public WorkingHoursDTO(WorkingHours workingHours) {
        this.id = workingHours.getId();
        this.startTime = workingHours.getStartTime();
        this.endTime = workingHours.getEndTime();
        this.driver = new DriverDTO(workingHours.getDriver());
    }
}
