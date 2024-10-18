package com.klearn.klearn_website.model;

import jakarta.persistence.*;

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

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "gender")
    private String gender;

    @Column(name = "last_login")
    private LocalDateTime last_login;

    @Column(name = "last_modified")
    private LocalDateTime last_modified;

    @Column(name = "is_deleted", columnDefinition = "BIT DEFAULT 0")
    private Boolean is_deleted;

    @Column(name = "role", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer role = 0; // 0: learner, 1: admin, 2: content-management

    @Column(name = "type")
    private String type;
}
