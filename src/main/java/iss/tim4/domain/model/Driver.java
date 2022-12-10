package iss.tim4.domain.model;

import iss.tim4.domain.dto.driver.DriverDTOResponse;
import jakarta.persistence.*;
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
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "profilePicture")  // nullable=true (default value)
    private String profilePicture;

    @Column(name = "telephoneNumber", unique = true, nullable = false)
    private String telephoneNumber;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    /* I nema neke velike potrebe da se u bazi cuva da li je korisnik trenutno aktivan, ali
    nek stoji da ne razmisljamo o tome, nek su svi atributi u bazi.
     */
    @Column(name = "active", nullable = false)
    private Boolean active;

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
