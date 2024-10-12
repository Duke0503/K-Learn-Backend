package com.klearn.klearn_website.service.auth;

import com.klearn.klearn_website.dto.dtoin.LoginDTOIn;
import com.klearn.klearn_website.dto.dtoin.RegisterDTOIn;

public interface AuthService {
    String login(LoginDTOIn loginDTOIn);
    String register(RegisterDTOIn registerDTOIn);
}
