package com.klearn.klearn_website.controller.user;

import com.klearn.klearn_website.dto.dtoin.CreateUserByAdminDTOIn;
import com.klearn.klearn_website.dto.dtoin.PasswordChangeDTOIn;
import com.klearn.klearn_website.dto.dtoin.PasswordResetDTOIn;
import com.klearn.klearn_website.dto.dtoin.UpdateUserByAdminDTOIn;
import com.klearn.klearn_website.dto.dtoin.UserUpdateDTOIn;
import com.klearn.klearn_website.dto.dtoout.MonthlyUserCountDTOOut;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.user.UserService;
import lombok.AllArgsConstructor;

import java.util.List;

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

    @GetMapping("/get-learners")
    public ResponseEntity<?> findLearners() {
        User user = userService.getAuthenticatedUser();

        // Allow access only if the user has role 1 or role 2
        if (user.getRole() != 1 && user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN);
        }

        List<User> learners = userService.findLearners();
        return new ResponseEntity<>(learners, HttpStatus.OK);
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> findUsers() {
        User user = userService.getAuthenticatedUser();

        // Allow access only if the user has role 1 or role 2
        if (user.getRole() != 1) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN);
        }

        List<User> users = userService.findAllUsers(user.getId());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/monthly_user_counts")

    public ResponseEntity<?> getMonthlyUserCounts() {
        User user = userService.getAuthenticatedUser();

        // Allow access only if the user has role 1 or role 2
        if (user.getRole() != 1) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN);
        }
        List<MonthlyUserCountDTOOut> monthlyCounts = userService.getMonthlyUserCounts();
        return ResponseEntity.ok(monthlyCounts);
    }

    @PostMapping("/create_user")
    public ResponseEntity<?> createUserByAdmin(@RequestBody CreateUserByAdminDTOIn createUserByAdminDTOIn) {
        User authenticatedUser = userService.getAuthenticatedUser();

        // Allow access only if the user has role 1 (Admin)
        if (authenticatedUser.getRole() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Unauthorized: You do not have permission to access this resource.");
        }

        try {
            userService.createUserByAdmin(createUserByAdminDTOIn);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User created successfully.");
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }

    @PutMapping("/update_user/{id}")
    public ResponseEntity<?> updateUserByAdmin(@PathVariable Integer id,
            @RequestBody UpdateUserByAdminDTOIn updateUserByAdminDTOIn) {
        User authenticatedUser = userService.getAuthenticatedUser();

        // Allow access only if the user has role 1 (Admin)
        if (authenticatedUser.getRole() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Unauthorized: You do not have permission to access this resource.");
        }

        try {
            userService.updateUserByAdmin(id, updateUserByAdminDTOIn);
            return ResponseEntity.ok("User updated successfully.");
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }
}
