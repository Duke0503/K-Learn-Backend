package com.klearn.klearn_website.dto.dtoin;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserByAdminDTOIn {
    private String fullname;

    private LocalDate dob;

    private String email;
    
    private Integer role;
}
