package com.klearn.klearn_website.service.user;

import com.klearn.klearn_website.mapper.PasswordResetTokenMapper;
import com.klearn.klearn_website.model.PasswordResetToken;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.security.JwtTokenProvider;

import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenMapper passwordResetTokenMapper;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates or updates a password reset token for a user.
     * If a token already exists for the user, updates the token value and expiry date.
     * Otherwise, creates a new token.
     *
     * @param user The user for whom the token is created or updated.
     * @return The updated or newly created PasswordResetToken entity.
     */
    public PasswordResetToken createOrUpdateToken(User user) {
        String newToken = generate4DigitCode();
        Optional<PasswordResetToken> existingTokenOpt = passwordResetTokenMapper.findByUserId(user.getId());
        LocalDateTime newExpiryDate = PasswordResetToken.calculateNewExpiryDate(PasswordResetToken.EXPIRATION_MINUTES);

        if (existingTokenOpt.isPresent()) {
            PasswordResetToken existingToken = existingTokenOpt.get();
            existingToken.setToken(newToken);
            existingToken.setExpiryDate(newExpiryDate);
            passwordResetTokenMapper.updateToken(existingToken);
            return existingToken;
        } else {
            PasswordResetToken newPasswordResetToken = new PasswordResetToken(newToken, user);
            newPasswordResetToken.setExpiryDate(newExpiryDate);
            passwordResetTokenMapper.createToken(newPasswordResetToken);
            return newPasswordResetToken;
        }
    }

    /**
     * Retrieves a password reset token by its token value and user ID.
     *
     * @param token The token value.
     * @param userId The user ID
     * @return An Optional containing the PasswordResetToken if found, or empty if not.
     */
    public Optional<PasswordResetToken> getTokenByValue(String token, Integer userId) {
        return passwordResetTokenMapper.findByTokenAndUserId(token, userId);
    }

    /**
     * Deletes a password reset token by its ID.
     *
     * @param id The ID of the token to delete.
     */
    public void deleteTokenById(Integer id) {
        passwordResetTokenMapper.deleteToken(id);
    }

    /**
     * Deletes all expired tokens.
     */
    public void deleteExpiredTokens() {
        passwordResetTokenMapper.deleteExpiredTokens(LocalDateTime.now());
    }

    /**
     * Generates a random 4-digit code as a string.
     *
     * @return A 4-digit numeric code as a string.
     */
    private String generate4DigitCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999
        return String.valueOf(code);
    }

     public Optional<String> validateTokenAndAuthenticate(String email, String code) {
        Optional<User> userOptional = userService.getUser(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Validate the reset token
            Optional<PasswordResetToken> tokenOptional = passwordResetTokenMapper.findByTokenAndUserId(code, user.getId());
            if (tokenOptional.isPresent() && !tokenOptional.get().isExpired()) {
                
                // Generate a secure temporary password and update it in the database
                String temporaryPassword = generateSecureTemporaryPassword();
                userService.updatePassword(user.getId(), passwordEncoder.encode(temporaryPassword));

                // Authenticate using the temporary password
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, temporaryPassword)
                );

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Generate and return the JWT token
                String jwtToken = jwtTokenProvider.generateToken(authentication);

                // Invalidate the reset token after use
                deleteTokenById(tokenOptional.get().getId());

                return Optional.of(jwtToken);
            }
        }
        return Optional.empty(); // Return empty if user or token is invalid
    }

    /**
     * Helper method to generate a secure temporary password.
     * 
     * @return A secure 8-character temporary password.
     */
    private String generateSecureTemporaryPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

}
