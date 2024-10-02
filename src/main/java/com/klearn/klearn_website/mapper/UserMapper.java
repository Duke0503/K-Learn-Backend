package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

  // Find a user by email or username
  @Select("SELECT * FROM users WHERE email = #{email} OR username = #{username}")
  User findByUsernameOrEmail(@Param("email") String email, @Param("username") String username);

  // Create a new user
  @Insert("INSERT INTO users (username, password, email, fullname, dob, gender, last_login, last_modified) " +
          "VALUES (#{username}, #{password}, #{email}, #{fullname}, #{dob}, #{gender}, #{last_login}, #{last_modified})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void createUser(User user);

  // Check if a user exists by email or username
  @Select("SELECT COUNT(*) FROM users WHERE email = #{email} OR username = #{username}")
  Boolean existsByUsernameOrEmail(@Param("email") String email, @Param("username") String username);

  // Update the last login time of a user
  @Update("UPDATE users SET last_login = #{last_login} WHERE id = #{id}")
  void updateLastLogin(@Param("id") Integer id, @Param("last_login") LocalDateTime last_login);
}
