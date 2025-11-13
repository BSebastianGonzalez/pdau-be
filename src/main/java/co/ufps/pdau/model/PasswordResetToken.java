package co.ufps.pdau.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public PasswordResetToken(String token, Admin admin, LocalDateTime expiryDate) {
        this.token = token;
        this.admin = admin;
        this.expiryDate = expiryDate;
    }
}