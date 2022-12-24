package iss.tim4.domain.model;

import iss.tim4.domain.dto.driver.document.DriverDocumentDTOResponse;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class DriverDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "document_image")
    private String documentImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    private Driver driver;

    public DriverDocument(DriverDocumentDTOResponse driverDocumentDTOResponse, Driver driver) {
        this.name = driverDocumentDTOResponse.getName();
        this.documentImage = driverDocumentDTOResponse.getDocumentImage();
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DriverDocument that = (DriverDocument) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
