package com.klearn.klearn_website.dto.dtoin;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateDTOIn {
    private String fullname;

    private LocalDate dob;

    private String email;
    
    private String gender;

    private String avatar;
}