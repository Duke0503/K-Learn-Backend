package com.klearn.klearn_website.service.auth;

import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.security.JwtTokenProvider;
import com.klearn.klearn_website.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Processes OAuth2 post login.
     * Creates a new user if the user does not exist or updates the last login time.
     * Generates a JWT token for the authenticated user.
     *
     * @param email The email from OAuth2 user.
     * @param oAuth2User The OAuth2 user object containing additional user details.
     * @return JWT token for authenticated session.
     */
    public String processOAuthPostLogin(String email, OAuth2User oAuth2User) {
        // Retrieve user using UserService
        Optional<User> existingUserOptional = userService.getUser(email);
        
        if (existingUserOptional.isEmpty()) {
            // If the user does not exist, create a new user
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(oAuth2User.getAttribute("email"));
            newUser.setFullname(oAuth2User.getAttribute("family_name") + " " + oAuth2User.getAttribute("given_name"));
            newUser.setPassword(""); // Password is left empty as this is an OAuth user
            newUser.setRole(0); // Default role is learner
            newUser.setAvatar(oAuth2User.getAttribute("picture"));
            newUser.setDob(LocalDate.of(2000, 1, 1));
            newUser.setLast_login(LocalDateTime.now());
            newUser.setCreated_at(LocalDateTime.now());
            newUser.setLast_modified(LocalDateTime.now());
            newUser.setGender("Nam");
            newUser.setIs_deleted(false);
            newUser.setType("email");

            // Create the user using UserService
            userService.createUser(newUser);
        } else {
            // If the user exists, update the last login time
            User existingUser = existingUserOptional.get();
            userService.updateLastLogin(existingUser.getId(), LocalDateTime.now());
        }

        // Authenticate the user
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate and return the JWT token
        return jwtTokenProvider.generateToken(authentication);
    }
}
