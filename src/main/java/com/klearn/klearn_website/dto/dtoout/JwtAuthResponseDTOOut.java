package com.klearn.klearn_website.dto.dtoout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponseDTOOut {
    private String accessToken;
    private String tokenType = "Bearer";
    private String roleName;
}