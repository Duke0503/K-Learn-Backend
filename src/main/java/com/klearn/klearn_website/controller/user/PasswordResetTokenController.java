package com.klearn.klearn_website.controller.user;

import com.klearn.klearn_website.model.PasswordResetToken;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.email.EmailService;
import com.klearn.klearn_website.service.user.PasswordResetTokenService;
import com.klearn.klearn_website.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class PasswordResetTokenController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    /**
     * Endpoint to generate and send a password reset token to the user's email.
     *
     * URL: /api/user/reset-password-token/{email}
     * Method: POST
     *
     * @param email The email of the user requesting the password reset.
     * @return ResponseEntity indicating the status of the request.
     */
    @PostMapping("/reset-password-token/{email}")
    public ResponseEntity<String> sendResetToken(@PathVariable String email) {
        // Retrieve the user by email
        Optional<User> userOptional = userService.getUser(email);

        // Check if the user exists
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getType() != "email") {
                // Generate or update the password reset token
                PasswordResetToken resetToken = passwordResetTokenService.createOrUpdateToken(user);

                // Send the token to the user's email
                String subject = "Password Reset Request";
                String message = "Your password reset token is: " + resetToken.getToken();
                emailService.sendEmail(user.getEmail(), subject, message);

                return ResponseEntity.ok("Password reset token sent to " + email);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found");
            }
        } else {
            // Return 404 if the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found");
        }
    }

    /**
     * Endpoint to validate the password reset token and authenticate the user if
     * valid.
     *
     * URL: /api/user/reset-password-auth/{email}/{code}
     * Method: POST
     *
     * @param email The email of the user.
     * @param code  The password reset code.
     * @return ResponseEntity with JWT token if successful or error message if
     *         invalid.
     */
    @PostMapping("/reset-password-auth/{email}/{code}")
    public ResponseEntity<String> validateTokenAndAuthenticate(@PathVariable String email, @PathVariable String code) {
        Optional<String> jwtTokenOptional = passwordResetTokenService.validateTokenAndAuthenticate(email, code);

        if (jwtTokenOptional.isPresent()) {
            return ResponseEntity.ok(jwtTokenOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token, or user not found.");
        }
    }
}
