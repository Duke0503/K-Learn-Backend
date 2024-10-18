package com.klearn.klearn_website.service.auth;

import com.klearn.klearn_website.dto.dtoin.LoginDTOIn;
import com.klearn.klearn_website.dto.dtoin.RegisterDTOIn;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.security.JwtTokenProvider;
import com.klearn.klearn_website.service.user.UserService;
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
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDTOIn loginDTOIn) {
        // Authenticate the user using AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTOIn.getUsernameOrEmail(),
                        loginDTOIn.getPassword()));

        // Retrieve the user using UserService
        User user = userService.getUser(loginDTOIn.getUsernameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email: " + loginDTOIn.getUsernameOrEmail()));

        // Update the last login time for the user
        userService.updateLastLogin(user.getId(), LocalDateTime.now());

        // Set the authentication context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate and return the JWT token
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDTOIn registerDTOIn) {
        // Check if the email or username already exists
        if (userService.userExists(registerDTOIn.getEmail()) || userService.userExists(registerDTOIn.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or username already exists");
        }

        // Check if the passwords match
        if (!registerDTOIn.getPassword().equals(registerDTOIn.getRe_password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        // Create a new user instance
        User user = new User();
        user.setFullname(registerDTOIn.getFirstname() + " " + registerDTOIn.getLastname());
        user.setEmail(registerDTOIn.getEmail());
        user.setUsername(registerDTOIn.getUsername());
        user.setDob(registerDTOIn.getDob());
        user.setGender(registerDTOIn.getGender());
        user.setLast_login(LocalDateTime.now());
        user.setLast_modified(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(registerDTOIn.getPassword()));
        user.setRole(0); // 0 for learner, 1 for admin, 2 for content-management
        user.setIs_deleted(false);
        user.setType("normal");
        user.setAvatar(null);
        // Create the user using UserService
        userService.createUser(user);

        // Authenticate the user after registration
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerDTOIn.getUsername(), registerDTOIn.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate and return the JWT token
        return jwtTokenProvider.generateToken(authentication);
    }
}
