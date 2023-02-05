package iss.tim4.domain.dto.working.hours;

import iss.tim4.domain.dto.driver.DriverDTOResult;
import iss.tim4.domain.model.WorkingHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursDTOResponse {
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime end;


    public WorkingHoursDTOResponse(WorkingHours workingHours) {
        this.start = workingHours.getStart();
        this.end = workingHours.getEnd();
    }
}
