package iss.tim4.domain.dto.driver.document;

import iss.tim4.domain.model.DriverDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDocumentDTOResult {

    private Integer id;
    @NotEmpty (message = "Field name is required!")
    private String name;
    @NotEmpty (message = "Field documentImage is required!")
    private String documentImage;
    @NotNull (message = "Field driverId is required!")
    private Integer driverId;

    public DriverDocumentDTOResult(DriverDocument driverDocument) {
        this.id = Long.valueOf(driverDocument.getId());
        this.name = driverDocument.getName();
        this.documentImage = driverDocument.getDocumentImage();
        this.driverId = driverDocument.getDriver().getId();
    }
}
