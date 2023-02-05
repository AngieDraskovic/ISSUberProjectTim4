package iss.tim4.domain.model;

import iss.tim4.domain.RequestStatus;
import iss.tim4.domain.RideStatus;
import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.driver.DriverDTOResponse;
import iss.tim4.domain.dto.driver.request.DriverRequestDTORequest;
import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class DriverRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status", nullable = false)
    private RequestStatus status;



    // ABOUT DRIVER

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    private Driver driver;

    @Column(name = "new_name", nullable = false)
    private String newName;

    @Column(name = "new_surname", nullable = false)
    private String newSurname;

    @Column(name = "new_profilePicture")  // nullable=true (default value)
    private String newProfilePicture;

    @Column(name = "new_telephoneNumber", nullable = false)
    private String newTelephoneNumber;

    @Column(name = "new_email", nullable = false)
    private String newEmail;

    @Column(name = "new_address", nullable = false)
    private String newAddress;



    // ABOUT VEHICLE

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    @ToString.Exclude
    private Vehicle vehicle;

    @Column(name = "new_model", nullable = false)
    private String newModel;

    @Column(name = "new_vehicle_name", nullable = false)
    private VehicleName newVehicleName;

    @Column(name = "new_reg_plates", nullable = false)
    private String newRegPlates;

    @Column(name = "new_num_seats", nullable = false)
    private Integer newNumSeats;

    @Column(name = "new_baby_proof", nullable = false)
    private Boolean newBabyProof;

    @Column(name = "new_pets_allowed", nullable = false)
    private Boolean newPetsAllowed;

    public DriverRequest(Driver driver, Vehicle vehicle, DriverRequestDTORequest driverRequestDTORequest) {
        this.status = RequestStatus.REVIEW;
        this.driver = driver;
        this.vehicle = vehicle;

        this.newName = driverRequestDTORequest.getNewName();
        this.newSurname = driverRequestDTORequest.getNewSurname();
        this.newProfilePicture = driverRequestDTORequest.getNewProfilePicture();
        this.newTelephoneNumber = driverRequestDTORequest.getNewTelephoneNumber();
        this.newEmail = driverRequestDTORequest.getNewEmail();
        this.newAddress = driverRequestDTORequest.getNewAddress();
        this.newModel = driverRequestDTORequest.getNewModel();
        this.newVehicleName =driverRequestDTORequest.getNewVehicleName();
        this.newRegPlates = driverRequestDTORequest.getNewRegPlates();
        this.newNumSeats = driverRequestDTORequest.getNewNumSeats();
        this.newBabyProof = driverRequestDTORequest.getNewBabyProof();
        this.newPetsAllowed = driverRequestDTORequest.getNewPetsAllowed();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DriverRequest driverRequest = (DriverRequest) o;
        return id != null && Objects.equals(id, driverRequest.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
