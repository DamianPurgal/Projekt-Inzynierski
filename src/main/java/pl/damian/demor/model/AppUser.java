package pl.damian.demor.model;

import lombok.*;
import pl.damian.demor.security.UserRole;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USERS")
public class AppUser {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name="user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name="locked")
    private Boolean locked = false;

    @Column(name="enabled")
    private Boolean enabled = true;
}
