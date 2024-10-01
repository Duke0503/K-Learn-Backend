package com.klearn.klearn_website.service.auth;

import com.klearn.klearn_website.dto.LoginDto;
import com.klearn.klearn_website.dto.RegisterDto;
import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );
        
        User user = userMapper.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
      
        userMapper.updateLastLogin(user.getId(), LocalDateTime.now());
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generate_token(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userMapper.existsByUsernameOrEmail(registerDto.getEmail(), registerDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or username already exists");
        }
    
        if (!registerDto.getPassword().equals(registerDto.getRe_password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
    
        User user = new User();
        user.setFull_name(registerDto.getFirstname() + " " + registerDto.getLastname());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setDob(registerDto.getDob());
        user.setGender(registerDto.getGender());
        user.setLast_modified(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        user.setRole(0);  // 0 for learner, 1 for admin, 2 for content-management

        userMapper.createUser(user);

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(registerDto.getUsername(), registerDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generate_token(authentication);
    }
    

}
