package iss.tim4.domain.dto;

import iss.tim4.domain.model.DriverDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDocumentDTOResponse {

    private String name;
    private String documentImage;


    public DriverDocumentDTOResponse(DriverDocument driverDocument) {
        this.name = driverDocument.getName();
        this.documentImage = driverDocument.getDocumentImage();
    }
}
