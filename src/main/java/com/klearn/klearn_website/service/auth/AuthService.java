package com.klearn.klearn_website.service.auth;

import com.klearn.klearn_website.dto.auth.LoginDto;
import com.klearn.klearn_website.dto.auth.RegisterDto;
public interface AuthService {
    String login(LoginDto loginDto); 
    String register(RegisterDto registerDto); 
}

