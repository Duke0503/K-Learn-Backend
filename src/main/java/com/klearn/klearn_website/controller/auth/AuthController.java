package com.klearn.klearn_website.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.dto.dtoin.LoginDTOIn;
import com.klearn.klearn_website.dto.dtoin.RegisterDTOIn;
import com.klearn.klearn_website.dto.dtoout.JwtAuthResponseDTOOut;
import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.service.auth.AuthService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserMapper userMapper;
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTOOut> login(@RequestBody LoginDTOIn loginDTOIn) {
        String token = authService.login(loginDTOIn);

        JwtAuthResponseDTOOut jwtAuthResponse = new JwtAuthResponseDTOOut();
        jwtAuthResponse.setAccessToken(token);

        Integer role = userMapper.getRole(loginDTOIn.getUsernameOrEmail(), loginDTOIn.getUsernameOrEmail());

        switch (role) {
            case 0:
                jwtAuthResponse.setRoleName("user");
                break;
            case 1:
                jwtAuthResponse.setRoleName("admin");
                break;
            default:
                jwtAuthResponse.setRoleName("content-management");
        }

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponseDTOOut> register(@RequestBody RegisterDTOIn registerDTOIn) {
        String token = authService.register(registerDTOIn);

        JwtAuthResponseDTOOut jwtAuthResponse = new JwtAuthResponseDTOOut();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRoleName("user");

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
    }
}
