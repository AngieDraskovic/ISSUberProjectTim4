package iss.tim4.domain.model;

import iss.tim4.domain.dto.driver.DriverDTOResponse;
import javax.persistence.*;

import iss.tim4.domain.dto.driver.request.DriverDTOUpdate;
import iss.tim4.domain.dto.ride.RideDTOExample;
import iss.tim4.domain.dto.ride.RideDTORequest;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User {

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<DriverDocument> documents = new HashSet<DriverDocument>();

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Ride> rides = new HashSet<Ride>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @ToString.Exclude
    private Vehicle vehicle;


    public Driver(DriverDTOResponse driverDTOResponse) {
        this.name = driverDTOResponse.getName();
        this.surname = driverDTOResponse.getSurname();
        this.profilePicture = driverDTOResponse.getProfilePicture();
        this.telephoneNumber = driverDTOResponse.getTelephoneNumber();
        this.email = driverDTOResponse.getEmail();
        this.address = driverDTOResponse.getAddress();
        this.password = driverDTOResponse.getPassword();
        this.active = false;
        this.blocked = false;
    }

    public void addDocument(DriverDocument document){
        documents.add(document);
        document.setDriver(this);
    }

    public void addRide(Ride ride) {
        rides.add(ride);
        ride.setDriver(this);
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        vehicle.setDriver(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Driver driver = (Driver) o;
        return id != null && Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void updateDriver(DriverDTOResponse driverDTOResponse) {
        this.name = driverDTOResponse.getName();
        this.surname = driverDTOResponse.getSurname();
        this.profilePicture = driverDTOResponse.getProfilePicture();
        this.telephoneNumber = driverDTOResponse.getTelephoneNumber();
        this.email = driverDTOResponse.getEmail();
        this.address = driverDTOResponse.getAddress();
        this.password = driverDTOResponse.getPassword();
    }

    public void updateDriver(DriverDTOUpdate driverDTOResponse) {
        this.name = driverDTOResponse.getName();
        this.surname = driverDTOResponse.getSurname();
        this.profilePicture = driverDTOResponse.getProfilePicture();
        this.telephoneNumber = driverDTOResponse.getTelephoneNumber();
        this.email = driverDTOResponse.getEmail();
        this.address = driverDTOResponse.getAddress();
       // this.password = driverDTOResponse.getPassword();
    }

    public boolean isAvailable(RideDTORequest newRide) {
        for (Ride ride : this.rides) {
            if (this.isBusy(ride, newRide))
                return false;
        }
        return true;
    }

    public boolean isBusy(Ride ride, RideDTORequest newRide) {
        return ride.getStartTime().isBefore(newRide.getStartTime().plusMinutes( newRide.getEstimatedTime().longValue()))
                && newRide.getStartTime().isBefore(ride.getStartTime().plusMinutes((long) ride.getEstimatedTimeInMinutes().doubleValue()));
    }

    public boolean isBusy2(Ride ride, Ride newRide) {
        return ride.getStartTime().isBefore(newRide.getStartTime().plusMinutes( newRide.getEstimatedTimeInMinutes().longValue()))
                && newRide.getStartTime().isBefore(ride.getStartTime().plusMinutes((long) ride.getEstimatedTimeInMinutes().doubleValue()));
    }


    public boolean compatibileVehicle(RideDTORequest rideDTO) {
        if (!rideDTO.getVehicleType().equals(this.vehicle.getVehicleName()))
            return false;
        if (rideDTO.getPetTransport() && !this.vehicle.getPetsAllowed())
            return false;
        return !rideDTO.getBabyTransport() || this.vehicle.getBabyProof();
    }
}
