package com.klearn.klearn_website.controller.auth;

import com.klearn.klearn_website.dto.dtoin.LoginDTOIn;
import com.klearn.klearn_website.dto.dtoin.RegisterDTOIn;
import com.klearn.klearn_website.dto.dtoout.JwtAuthResponseDTOOut;
import com.klearn.klearn_website.service.auth.AuthService;
import com.klearn.klearn_website.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication-related requests.
 * This includes endpoints for login and registration.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * Endpoint for logging in a user.
     * 
     * URL: /api/auth/login
     * Method: POST
     * 
     * @param loginDTOIn Contains login information including username or email and password.
     * @return JwtAuthResponseDTOOut containing the JWT access token and the user's role.
     * @throws UsernameNotFoundException if the username or email does not exist.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTOOut> login(@RequestBody LoginDTOIn loginDTOIn) {
        // Authenticate the user and generate a JWT token
        String token = authService.login(loginDTOIn);

        // Create response DTO with the JWT token
        JwtAuthResponseDTOOut jwtAuthResponse = new JwtAuthResponseDTOOut();
        jwtAuthResponse.setAccessToken(token);

        // Retrieve user role and add to response DTO using the service
        Integer role = userService.getUserRole(loginDTOIn.getUsernameOrEmail());

        // Set the role name based on the retrieved role value
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

        // Return the response entity with OK status
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    /**
     * Endpoint for registering a new user.
     * 
     * URL: /api/auth/register
     * Method: POST
     * 
     * @param registerDTOIn Contains registration details including username, email, password, etc.
     * @return JwtAuthResponseDTOOut containing the JWT access token and default role as "user".
     */
    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponseDTOOut> register(@RequestBody RegisterDTOIn registerDTOIn) {
        // Register the user and generate a JWT token
        String token = authService.register(registerDTOIn);

        // Create response DTO with the JWT token and default role as "user"
        JwtAuthResponseDTOOut jwtAuthResponse = new JwtAuthResponseDTOOut();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRoleName("user");

        // Return the response entity with CREATED status
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
    }
}
