package com.klearn.klearn_website.service.auth;

import com.klearn.klearn_website.dto.dtoin.LoginDTOIn;
import com.klearn.klearn_website.dto.dtoin.RegisterDTOIn;
import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public String login(LoginDTOIn loginDTOIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTOIn.getUsernameOrEmail(),
                    loginDTOIn.getPassword()
                )
        );

        User user = userMapper.findByUsernameOrEmail(loginDTOIn.getUsernameOrEmail(), loginDTOIn.getUsernameOrEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email: " + loginDTOIn.getUsernameOrEmail());
        }
        userMapper.updateLastLogin(user.getId(), LocalDateTime.now());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDTOIn registerDTOIn) {
        if (userMapper.existsByUsernameOrEmail(registerDTOIn.getEmail(), registerDTOIn.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or username already exists");
        }

        if (!registerDTOIn.getPassword().equals(registerDTOIn.getRe_password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        User user = new User();
        user.setFullname(registerDTOIn.getFirstname() + " " + registerDTOIn.getLastname());
        user.setEmail(registerDTOIn.getEmail());
        user.setUsername(registerDTOIn.getUsername());
        user.setDob(registerDTOIn.getDob());
        user.setGender(registerDTOIn.getGender());
        user.setLast_login(LocalDateTime.now());
        user.setLast_modified(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(registerDTOIn.getPassword()));
        user.setRole(0);  // 0 for learner, 1 for admin, 2 for content-management

        userMapper.createUser(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerDTOIn.getUsername(), registerDTOIn.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

}
