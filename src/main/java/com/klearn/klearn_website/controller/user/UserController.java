package com.klearn.klearn_website.controller.user;

import com.klearn.klearn_website.dto.dtoin.PasswordChangeDTOIn;
import com.klearn.klearn_website.dto.dtoin.PasswordResetDTOIn;
import com.klearn.klearn_website.dto.dtoin.UserEmailUpdateDTOIn;
import com.klearn.klearn_website.dto.dtoin.UserUpdateDTOIn;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user-related requests.
 * Provides endpoints for retrieving, updating, and managing user profiles.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordResetDTOIn passwordUpdateRequest) {
        // Check if newPassword and reNewPassword are equal
        if (!passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getReNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match.");
        }

        // Get the authenticated user and update password
        User user = userService.getAuthenticatedUser();
        userService.updatePassword(user.getId(), passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        return ResponseEntity.ok("Password updated successfully.");
    }

    /**
     * Get the authenticated user's profile.
     */
    @GetMapping("/profile")
    public ResponseEntity<User> getAuthenticatedUser() {
        User user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserUpdateDTOIn userUpdateRequest) {
        User user = userService.getAuthenticatedUser();

        // Update user fields
        user.setFullname(userUpdateRequest.getFullname());
        user.setDob(userUpdateRequest.getDob());
        user.setEmail(userUpdateRequest.getEmail());
        user.setGender(userUpdateRequest.getGender());
        user.setAvatar(userUpdateRequest.getAvatar());

        userService.updateUser(user); // Save updated user information

        return ResponseEntity.ok("User profile updated successfully.");
    }

    @PutMapping("/update-profile-account-email")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserEmailUpdateDTOIn userEmailUpdateDTOIn) {
        User user = userService.getAuthenticatedUser();

        // Update user fields
        user.setFullname(userEmailUpdateDTOIn.getFullname());
        user.setDob(userEmailUpdateDTOIn.getDob());
        user.setGender(userEmailUpdateDTOIn.getGender());

        userService.updateUser(user); // Save updated user information

        return ResponseEntity.ok("User profile updated successfully.");
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTOIn passwordChangeRequest) {
        User user = userService.getAuthenticatedUser();

        // Step 1: Check if the present password is correct
        if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect.");
        }

        // Step 2: Verify that newPassword and reNewPassword are equal
        if (!passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New passwords do not match.");
        }

        // Step 4: Update password
        userService.updatePassword(user.getId(), passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        return ResponseEntity.ok("Password changed successfully.");
    }
}
