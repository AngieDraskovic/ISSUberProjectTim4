package iss.tim4.domain.dto.driver.request;

import iss.tim4.domain.model.DriverRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DriverRequestDTORequest {


    // ABOUT DRIVER
    private Long driverId;
    private String newName;
    private String newSurname;
    private String newProfilePicture;
    private String newTelephoneNumber;
    private String newEmail;
    private String newAddress;



    // ABOUT VEHICLE
    private Long vehicleId;
    private String newModel;
    private String newVehicleName;
    private String newRegPlates;
    private Integer newNumSeats;
    private Boolean newBabyProof;
    private Boolean newPetsAllowed;


    public DriverRequestDTORequest(DriverRequest driverRequest) {
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
