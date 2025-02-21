package org.example.gdfutureserver.password.model;


import jakarta.persistence.*;
import lombok.*;
import org.example.gdfutureserver.users.model.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {

    @SequenceGenerator(name = "password_reset_token_sequence", sequenceName = "password_reset_token_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_token_sequence")
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "token", nullable = false,unique = true)
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;


}
