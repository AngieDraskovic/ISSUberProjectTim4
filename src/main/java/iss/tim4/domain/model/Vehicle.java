package iss.tim4.domain.model;

import iss.tim4.domain.VehicleName;
import iss.tim4.domain.dto.VehicleDTOResponse;
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
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "vehicle_name", nullable = false)
    private VehicleName vehicleName;

    @Column(name = "reg_plates", nullable = false)
    private String regPlates;

    @Column(name = "num_seats", nullable = false)
    private Integer numSeats;

    @Column(name = "baby_proof", nullable = false)
    private Boolean babyProof;

    @Column(name = "pets_allowed", nullable = false)
    private Boolean petsAllowed;

    @Column(name="available", nullable = false)
    private Boolean available;

    @OneToOne(mappedBy = "vehicle")
    private Driver driver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location currLocation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    @ToString.Exclude
    private Set<Review> reviews = new HashSet<Review>();



    public Vehicle(Driver driver, VehicleDTOResponse vehicleDTOResponse) {
        this.model = vehicleDTOResponse.getModel();
        this.vehicleName = vehicleDTOResponse.getVehicleType();
        this.regPlates = vehicleDTOResponse.getLicenseNumber();
        this.numSeats = vehicleDTOResponse.getPassengerSeats();
        if(vehicleDTOResponse.getLicenseNumber()==null) {
            this.currLocation = new Location(vehicleDTOResponse.getCurrentLocation());
        }else{
            this.currLocation = new Location("Svetislava Kasapinovica broj 33", 42.111, 43.11);

        }
        this.babyProof = vehicleDTOResponse.getBabyTransport();
        this.petsAllowed = vehicleDTOResponse.getPetTransport();
        this.driver = driver;
        if(vehicleDTOResponse.getAvailable() == null) {
            this.available = true;
        }else{
            this.available = vehicleDTOResponse.getAvailable();
        }
    }


    public void addReview(Review review) {
        reviews.add(review);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vehicle vehicle = (Vehicle) o;
        return id != null && Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
