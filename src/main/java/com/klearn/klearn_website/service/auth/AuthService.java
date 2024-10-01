package com.klearn.klearn_website.service.auth;

import com.klearn.klearn_website.dto.LoginDto;
import com.klearn.klearn_website.dto.RegisterDto;
public interface AuthService {
    String login(LoginDto loginDto); 
    String register(RegisterDto registerDto); 
}

