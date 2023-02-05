package iss.tim4.domain.dto.driver;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
public class DriverDTORequest {

    private int totalCount;
    @Valid
    private DriverDTOResult[] results;

    public DriverDTORequest(int totalCount, DriverDTOResult[] results) {
        this.totalCount = totalCount;
        this.results = results;
    }
}
