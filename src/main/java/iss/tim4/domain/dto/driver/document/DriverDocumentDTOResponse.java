package iss.tim4.domain.dto.driver.document;

import iss.tim4.domain.model.DriverDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDocumentDTOResponse {

    @NotEmpty (message = "Field name is required!")
    private String name;
    @NotEmpty (message = "Field documentImage is required!")
    private String documentImage;


    public DriverDocumentDTOResponse(DriverDocument driverDocument) {
        this.name = driverDocument.getName();
        this.documentImage = driverDocument.getDocumentImage();
    }
}
