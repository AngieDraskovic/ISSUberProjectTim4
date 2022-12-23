package iss.tim4.domain.dto.driver.request;

import iss.tim4.domain.RequestStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.model.Driver;
import iss.tim4.domain.model.DriverRequest;
import iss.tim4.domain.model.Vehicle;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
public class DriverRequestDTOResult {

    // ABOUT REQUEST
    private Integer id;
    private String status;



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
