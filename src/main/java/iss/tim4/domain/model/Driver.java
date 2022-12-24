package iss.tim4.domain.model;

import iss.tim4.domain.dto.driver.DriverDTOResponse;
import javax.persistence.*;
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
}
