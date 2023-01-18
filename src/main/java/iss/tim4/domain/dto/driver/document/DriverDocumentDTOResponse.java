package iss.tim4.domain.dto.driver.document;

import iss.tim4.domain.model.DriverDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDocumentDTOResponse {

    @Size(min = 6, max = 30, message = "Field name format is not valid!")
    private String name;
    @NotEmpty (message = "Field documentImage is required!")
    private String documentImage;


    public DriverDocumentDTOResponse(DriverDocument driverDocument) {
        this.name = driverDocument.getName();
        this.documentImage = driverDocument.getDocumentImage();
    }
}
