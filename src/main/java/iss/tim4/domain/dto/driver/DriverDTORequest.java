package iss.tim4.domain.dto.driver;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DriverDTORequest {

    private int totalCount;
    private DriverDTOResult[] results;

    public DriverDTORequest(int totalCount, DriverDTOResult[] results) {
        this.totalCount = totalCount;
        this.results = results;
    }
}
