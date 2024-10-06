package com.klearn.klearn_website.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.dto.auth.JwtAuthResponse;
import com.klearn.klearn_website.dto.auth.LoginDto;
import com.klearn.klearn_website.dto.auth.RegisterDto;
import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.service.auth.AuthService;


@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserMapper userMapper;
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        Integer role = userMapper.getRole(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
        
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
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterDto registerDto) {
        String token = authService.register(registerDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
    }

}
