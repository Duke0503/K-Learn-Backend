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
@Table(name="users")

public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  private String password;

  private String full_name;

  private LocalDate dob;

  private String avatar;

  private Boolean gender;

  private LocalDateTime last_login; 

  private LocalDateTime last_modified;

  private Boolean is_deleted;
  
  @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
  private Integer role = 0;  // 0: learner, 1: admin, 2: content-management

    
}
