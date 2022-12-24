package iss.tim4.domain.model;

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
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "surname", nullable = false)
    protected String surname;

    @Column(name = "profile_picture")  // nullable=true (default value)
    protected String profilePicture;

    @Column(name = "telephone_number", unique = true, nullable = false)
    protected String telephoneNumber;

    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Column(name = "address", nullable = false)
    protected String address;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "blocked", nullable = false)
    protected Boolean blocked;

    @Column(name = "active", nullable = false)
    protected Boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    protected Set<Remark> remarks = new HashSet<Remark>();

    public void addRemark(Remark remark){
        remarks.add(remark);
        remark.setUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public org.springframework.security.core.userdetails.UserDetails toUserDetails() {
        return org.springframework.security.core.userdetails.User
                .withUsername(this.getUsername())
                .password(this.getPassword())
                .roles(this.getRole().toString())
                .build();
    }
}
