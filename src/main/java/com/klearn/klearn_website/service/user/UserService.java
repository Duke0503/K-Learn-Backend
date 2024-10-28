package com.klearn.klearn_website.service.user;

import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.model.User;
import lombok.AllArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    // Find a user by email or username
    public Optional<User> getUser(String emailOrUsername) {
        return userMapper.findByUsernameOrEmail(emailOrUsername, emailOrUsername);
    }

    // Find a user by id
    public Optional<User> getUserById(Integer userId) {
        return userMapper.findUserById(userId);
    }

    // Get the role of a user by email or username
    public Integer getUserRole(String emailOrUsername) {
        return userMapper.getRole(emailOrUsername, emailOrUsername);
    }

    // Check if a user exists by email or username
    public boolean userExists(String emailOrUsername) {
        return userMapper.countByUsernameOrEmail(emailOrUsername, emailOrUsername) > 0;
    }

    // Update the last login time of a user
    public void updateLastLogin(Integer userId, LocalDateTime lastLogin) {
        userMapper.updateLastLogin(userId, lastLogin);
    }

    // Create a new user
    public User createUser(User user) {
        user.setLast_modified(LocalDateTime.now());
        userMapper.createUser(user);
        return user;
    }

    // Update user details
    public void updateUser(User user) {
        user.setLast_modified(LocalDateTime.now());
        userMapper.updateUser(user);
    }

    // Update the user's password
    public void updatePassword(Integer userId, String newPassword) {
        userMapper.updatePassword(userId, newPassword);
    }

    // Soft delete a user
    public void softDeleteUser(Integer userId) {
        userMapper.softDeleteUser(userId);
    }

    // Un-delete a soft-deleted user
    public void unDeleteUser(Integer userId) {
        userMapper.unDeleteUser(userId);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return getUser(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    public List<User> findUsersNotLoggedInFor30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(31);
        LocalDateTime endDate = LocalDateTime.now().minusDays(30);

        return userMapper.findUsersNotLoggedInSince(startDate, endDate);
    }
}
