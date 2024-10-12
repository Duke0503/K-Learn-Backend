package com.klearn.klearn_website.dto.dtoin;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegisterDTOIn {
  private String firstname;

  private String lastname;

  private String email;

  private String username; 

  private LocalDate dob;

  private String gender;

  private String password;

  private String re_password;
}
