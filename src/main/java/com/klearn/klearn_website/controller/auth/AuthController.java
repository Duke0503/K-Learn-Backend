package com.klearn.klearn_website.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.dto.JwtAuthResponse;
import com.klearn.klearn_website.dto.LoginDto;
import com.klearn.klearn_website.dto.RegisterDto;
import com.klearn.klearn_website.service.auth.AuthService;


@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterDto registerDto) {
        String token = authService.register(registerDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
    }

}
