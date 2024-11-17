package com.klearn.klearn_website.service.user;

import com.klearn.klearn_website.dto.dtoin.CreateUserByAdminDTOIn;
import com.klearn.klearn_website.dto.dtoin.UpdateUserByAdminDTOIn;
import com.klearn.klearn_website.dto.dtoout.MonthlyUserCountDTOOut;
import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.model.User;
import lombok.AllArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
        if (user.getDob() == null) {
            user.setDob(LocalDate.of(2000, 1, 1));
        }
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

    public List<User> findLearners() {
        return userMapper.findAllLearners();
    }

    public List<User> findAllUsers(Integer id) {
        return userMapper.findAllUsers(id);
    }

    public List<MonthlyUserCountDTOOut> getMonthlyUserCounts() {
        return userMapper.getMonthlyUserCounts();
    }

    public void createUserByAdmin(CreateUserByAdminDTOIn createUserByAdminDTOIn) {
        // Check if the email or username already exists
        if (userExists(createUserByAdminDTOIn.getEmail()) || userExists(createUserByAdminDTOIn.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or username already exists");
        }

        // Check if the passwords match
        if (!createUserByAdminDTOIn.getPassword().equals(createUserByAdminDTOIn.getRe_password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        // Create a new user instance
        User user = new User();

        user.setFullname(createUserByAdminDTOIn.getFirstname() + " " + createUserByAdminDTOIn.getLastname());
        user.setEmail(createUserByAdminDTOIn.getEmail());
        user.setUsername(createUserByAdminDTOIn.getUsername());
        user.setDob(createUserByAdminDTOIn.getDob());
        user.setGender(createUserByAdminDTOIn.getGender());
        user.setLast_login(LocalDateTime.now());
        user.setLast_modified(LocalDateTime.now());
        user.setCreated_at(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(createUserByAdminDTOIn.getPassword()));
        user.setRole(createUserByAdminDTOIn.getRole()); // 0 for learner, 1 for admin, 2 for content-management
        user.setIs_deleted(false);
        user.setType("normal");
        user.setAvatar(null);

        createUser(user);
    }

    public void updateUserByAdmin(Integer userId, UpdateUserByAdminDTOIn updateUserByAdminDTOIn) {
        User user = getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        user.setFullname(updateUserByAdminDTOIn.getFullname());
        user.setEmail(updateUserByAdminDTOIn.getEmail());
        user.setDob(updateUserByAdminDTOIn.getDob());
        user.setRole(updateUserByAdminDTOIn.getRole());

        updateUser(user);
    }
}
