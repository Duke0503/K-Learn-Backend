package com.klearn.klearn_website.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username must be at most 50 characters")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password")
    private String password;

    @Size(max = 100, message = "Full name must be at most 100 characters")
    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "dob")
    private LocalDate dob;

    @Size(max = 255, message = "Avatar URL must be at most 255 characters")
    @Column(name = "avatar", length = 255)
    private String avatar;

    @Size(max = 10, message = "Gender must be at most 10 characters")
    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "last_login")
    private LocalDateTime last_login;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;

    @NotNull(message = "Deleted status cannot be null")
    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @NotNull(message = "Role cannot be null")
    @Column(name = "role", nullable = false)
    private Integer role = 0; // 0: learner, 1: admin, 2: content-management

    @Size(max = 50, message = "Type must be at most 50 characters")
    @Column(name = "type", length = 50)
    private String type;

    // Lifecycle callbacks for setting the timestamps
    @PrePersist
    protected void onCreate() {
        this.last_modified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.last_modified = LocalDateTime.now();
    }
}
