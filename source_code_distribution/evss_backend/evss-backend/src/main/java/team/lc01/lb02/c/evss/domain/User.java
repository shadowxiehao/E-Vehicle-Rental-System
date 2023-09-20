package team.lc01.lb02.c.evss.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import team.lc01.lb02.c.evss.util.Role;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u "),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u " +
                "WHERE u.email = :email"),
        @NamedQuery(name = "User.findByEmailAndPassword", query = "SELECT u FROM User u " +
                "WHERE u.email = :email AND u.password =: password"),
})
public class User extends BaseDomain {

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, unique = true)
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "invalid email format")
    private String email;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Lob
    @Column(name = "profile_picture", nullable = false, columnDefinition = "mediumblob")
    private byte[] profilePicture;

    @OneToMany
    @JoinColumn(name = "user")
    private Set<Ticket> tickets;

//    public String getNameAndRole() {
//        return String.join("", this.firstName, " ", this.lastName, " (", this.role.name(), ")");
//    }

//    public String getFullName() {
//        return String.join("", this.firstName, " ", this.lastName);
//    }

//    public String getImageBase64() {
//        return Base64.getEncoder().encodeToString(profilePicture);
//    }

    public void checkNull() {
        super.validate(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
