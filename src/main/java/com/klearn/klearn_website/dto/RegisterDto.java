package com.klearn.klearn_website.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegisterDto {
  private String firstname;

  private String lastname;

  private String email;

  private String username; 

  private LocalDate dob;

  private Boolean gender;

  private String password;

  private String re_password;
}
