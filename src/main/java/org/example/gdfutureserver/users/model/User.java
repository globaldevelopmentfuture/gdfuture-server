package org.example.gdfutureserver.users.model;


import jakarta.persistence.*;
import lombok.*;
import org.example.gdfutureserver.system.security.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Table(name = "user")
@Entity(name = "User")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )

    @Column(
            name = "id"
    )
    private long id;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            name = "full_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String fullName;


    @Column(
            name = "phone",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String phone;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;



    public User(Long id, String fullName, String phoneNumber, String email, String password, UserRole userRole, LocalDateTime registeredAt, LocalDateTime createdAt, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phoneNumber;
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.userRole = userRole;

    }

    public void setPassword(String password){
        this.password= new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getGrantedAuthorities();
    }

    @Override
    public String getUsername(){
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
