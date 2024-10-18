package com.klearn.klearn_website.controller.user;

import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller for handling user profile-related requests.
 * Provides an endpoint for retrieving the authenticated user's profile information.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class ProfileController {

    private final UserService userService;

    /**
     * Endpoint for retrieving the authenticated user's profile.
     * 
     * URL: /api/user/profile
     * Method: GET
     * 
     * @return ResponseEntity containing the authenticated user's profile information.
     *         Returns 404 if the user is not found.
     */
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the username from the authentication object
        String username = authentication.getName();

        // Retrieve user profile using the service layer
        Optional<User> userOptional = userService.getUser(username);

        // If the user is found, return it with OK status, otherwise return NOT FOUND status
        return userOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
