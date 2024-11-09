package com.klearn.klearn_website.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    public static final int EXPIRATION_MINUTES = 15; // 15 minutes expiration

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token", nullable = false)
    private String token;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    // Constructor to initialize the token and user with an automatic expiry date
    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION_MINUTES);
    }

    private LocalDateTime calculateExpiryDate(int expirationMinutes) {
        return LocalDateTime.now().plus(expirationMinutes, ChronoUnit.MINUTES);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    // Static method to calculate a new expiry date based on given minutes
    public static LocalDateTime calculateNewExpiryDate(int expirationMinutes) {
        return LocalDateTime.now().plus(expirationMinutes, ChronoUnit.MINUTES);
    }
}
