package com.klearn.klearn_website.dto.dtoin;

import lombok.Data;

@Data
public class PasswordChangeDTOIn {
    private String currentPassword;

    private String newPassword;
    
    private String confirmPassword;
}
