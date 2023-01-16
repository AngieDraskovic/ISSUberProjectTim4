package iss.tim4.domain.dto.driver.document;

import iss.tim4.domain.model.DriverDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDocumentDTOResult {

    private Integer id;
    private String name;
    private String documentImage;
    private Integer driverId;

    public DriverDocumentDTOResult(DriverDocument driverDocument) {
        this.id = driverDocument.getId();
        this.name = driverDocument.getName();
        this.documentImage = driverDocument.getDocumentImage();
        this.driverId = driverDocument.getDriver().getId();
    }
}
