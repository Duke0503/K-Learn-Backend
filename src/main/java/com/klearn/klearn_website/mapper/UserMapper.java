package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    // Find a user by email or username (excluding soft-deleted users)
    @Select("SELECT id, username, password, email, fullname, dob, avatar, gender, role, type, last_login, last_modified, is_deleted FROM users WHERE (email = #{email} OR username = #{username}) AND is_deleted = 0")
    Optional<User> findByUsernameOrEmail(@Param("email") String email, @Param("username") String username);

    // Find a user by id (excluding soft-deleted users)
    @Select("SELECT id, username, password, email, fullname, dob, avatar, gender, role, type, last_login, last_modified, is_deleted FROM users WHERE id = #{user_id} AND is_deleted = 0")
    Optional<User> findUserById(@Param("user_id") Integer userId);

    // Get Role
    @Select("SELECT role FROM users WHERE email = #{email} OR username = #{username}")
    Integer getRole(@Param("email") String email, @Param("username") String username);

    // Check if a user exists by email or username
    @Select("SELECT COUNT(*) FROM users WHERE email = #{email} OR username = #{username}")
    Integer countByUsernameOrEmail(@Param("email") String email, @Param("username") String username);

    // Update the last login time of a user
    @Update("UPDATE users SET last_login = #{last_login} WHERE id = #{id}")
    void updateLastLogin(@Param("id") Integer id, @Param("last_login") LocalDateTime lastLogin);

    // CRUD operations
    @Insert("INSERT INTO users (username, password, email, fullname, dob, gender, type, avatar, role, last_login, last_modified, is_deleted) "
            +
            "VALUES (#{username}, #{password}, #{email}, #{fullname}, #{dob}, #{gender}, #{type}, #{avatar}, #{role}, #{last_login}, #{last_modified}, #{is_deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createUser(User user);

    @Update("UPDATE users SET username = #{username}, email = #{email}, fullname = #{fullname}, dob = #{dob}, " +
            "gender = #{gender}, avatar = #{avatar}, role = #{role}, type = #{type}, last_modified = NOW() " +
            "WHERE id = #{id}")
    void updateUser(User user);

    @Update("UPDATE users SET password = #{password}, last_modified = NOW() WHERE id = #{id}")
    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    @Update("UPDATE users SET is_deleted = 1, last_modified = NOW() WHERE id = #{id}")
    void softDeleteUser(@Param("id") Integer id);

    @Update("UPDATE users SET is_deleted = 0, last_modified = NOW() WHERE id = #{id}")
    void unDeleteUser(@Param("id") Integer id);

    @Select("SELECT * FROM users WHERE last_login >= #{startDate} AND last_login < #{endDate} AND is_deleted = 0")
    List<User> findUsersNotLoggedInSince(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
  
    @Select("SELECT * FROM users WHERE role = 0 AND is_deleted = 0")
    List<User> findAllLearners();

    @Select("SELECT * FROM users WHERE is_deleted = 0 AND id != #{id}")
    List<User> findAllUsers(@Param("id") Integer id);
}
