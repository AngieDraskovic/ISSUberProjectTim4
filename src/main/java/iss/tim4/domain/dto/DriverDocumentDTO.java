package iss.tim4.domain.dto;

import iss.tim4.domain.model.DriverDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDocumentDTO {

    private Long id;
    private String name;
    private String imgPath;
    private DriverDTO driver;

    public DriverDocumentDTO(DriverDocument driverDocument) {
        this.id = driverDocument.getId();
        this.name = driverDocument.getName();
        this.imgPath = driverDocument.getImgPath();
        this.driver = new DriverDTO(driverDocument.getDriver());
    }
}
