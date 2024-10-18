package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

  // Find a user by email or username
  @Select("SELECT id, username, email, fullname, dob, avatar, gender, role, type FROM users WHERE email = #{email} OR username = #{username}")
  User findByUsernameOrEmail(@Param("email") String email, @Param("username") String username);

  // Find a user by id
  @Select("SELECT id, username, email, fullname, dob, avatar, gender, role, type FROM users WHERE id = #{user_id}")
  User findUserById(@Param("user_id") Integer userId);

  // Get Role
  @Select("SELECT role FROM users WHERE email = #{email} OR username = #{username}")
  Integer getRole(@Param("email") String email, @Param("username") String username);

  // Check if a user exists by email or username
  @Select("SELECT COUNT(*) FROM users WHERE email = #{email} OR username = #{username}")
  Boolean existsByUsernameOrEmail(@Param("email") String email, @Param("username") String username);

  // Update the last login time of a user
  @Update("UPDATE users SET last_login = #{last_login} WHERE id = #{id}")
  void updateLastLogin(@Param("id") Integer id, @Param("last_login") LocalDateTime last_login);

  // CRUD
  @Insert("INSERT INTO users (username, password, email, fullname, dob, gender, type, avatar, role, last_login, last_modified, is_deleted) " +
        "VALUES (#{username}, #{password}, #{email}, #{fullname}, #{dob}, #{gender}, #{type}, #{avatar}, #{role}, #{last_login}, #{last_modified}, #{is_deleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void createUser(User user);

  @Update("UPDATE users SET username = #{username}, password = #{password}, email = #{email}, fullname = #{fullname}, dob = #{dob}, " +
        "gender = #{gender}, avatar = #{avatar}, role = #{role}, type = #{type}, last_modified = #{last_modified} " +
        "WHERE id = #{id}")
  void updateUser(User user);

  @Update("UPDATE users SET is_deleted = 1 WHERE id = #{id}")
  void softDeleteUser(@Param("id") Integer id);

  @Update("UPDATE users SET last_modified = #{last_modified} WHERE id = #{id}")
  void updateLastModified(@Param("id") Integer id, @Param("last_modified") LocalDateTime lastModified);
}
