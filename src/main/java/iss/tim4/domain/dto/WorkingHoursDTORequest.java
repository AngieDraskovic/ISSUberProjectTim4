package iss.tim4.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursDTORequest {

    private int totalCount;
    private WorkingHoursDTOResult[] results;

    public WorkingHoursDTORequest(WorkingHoursDTOResult[] results) {
        this.totalCount = results.length;
        this.results = results;
    }
}
