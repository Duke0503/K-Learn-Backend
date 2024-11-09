package com.klearn.klearn_website.dto.dtoin;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserEmailUpdateDTOIn {
    private String fullname;

    private LocalDate dob;
    
    private String gender;
}