package iss.tim4.domain.dto.driver.request;

import iss.tim4.domain.RequestStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.DriverRequest;
import iss.tim4.domain.model.Vehicle;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
public class DriverRequestDTOResult {

    // ABOUT REQUEST
    private Integer id;
    @NotEmpty (message = "Field status is required!")
    private String status;



    // ABOUT DRIVER
    private Integer driverId;
    @NotEmpty (message = "Field newName is required!")
    private String newName;
    @NotEmpty (message = "Field newSurname is required!")
    private String newSurname;
    private String newProfilePicture;
    @Size(min = 6, max = 30, message = "Field newTelephoneNumber format is not valid!")
    private String newTelephoneNumber;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Field newEmail format is not valid!")
    private String newEmail;
    @NotEmpty(message = "Field newAddress is required!")
    private String newAddress;



    // ABOUT VEHICLE
    private Integer vehicleId;
    @NotEmpty (message = "Field newModel is required!")
    private String newModel;
    @NotEmpty (message = "Field newVehicleName is required!")
    private String newVehicleName;
    @NotEmpty (message = "Field newRegPlates is required!")
    private String newRegPlates;
    @Size(min = 2, max = 10, message = "Field newNumSeats format is not valid!")
    private Integer newNumSeats;
    @NotNull (message = "Field newBabyProof is required!")
    private Boolean newBabyProof;
    @NotNull (message = "Field newPetsAllowed is required!")
    private Boolean newPetsAllowed;


    public DriverRequestDTOResult(DriverRequest driverRequest) {
        this.id = driverRequest.getId();
        this.status = driverRequest.getStatus().toString();
        this.driverId = driverRequest.getDriver().getId();
        this.newName = driverRequest.getNewName();
        this.newSurname = driverRequest.getNewSurname();
        this.newProfilePicture = driverRequest.getNewProfilePicture();
        this.newTelephoneNumber = driverRequest.getNewTelephoneNumber();
        this.newEmail = driverRequest.getNewEmail();
        this.newAddress = driverRequest.getNewAddress();
        this.vehicleId = driverRequest.getVehicle().getId();
        this.newModel = driverRequest.getNewModel();
        this.newVehicleName = driverRequest.getNewVehicleName().toString();
        this.newRegPlates = driverRequest.getNewRegPlates();
        this.newNumSeats = driverRequest.getNewNumSeats();
        this.newBabyProof = driverRequest.getNewBabyProof();
        this.newPetsAllowed = driverRequest.getNewPetsAllowed();
    }
}
