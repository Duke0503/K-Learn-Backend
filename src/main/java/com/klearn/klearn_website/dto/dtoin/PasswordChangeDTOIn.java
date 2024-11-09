package com.klearn.klearn_website.dto.dtoin;

import lombok.Data;

@Data
public class PasswordChangeDTOIn {
    private String presentPassword;

    private String newPassword;
    
    private String reNewPassword;
}
