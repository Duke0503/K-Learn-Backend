package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM users WHERE email = #{email} OR username = #{username}")
  User findByUsernameOrEmail(@Param("email") String email, @Param("username") String username);
  
  @Insert("INSERT INTO users (username, password, email, full_name, dob, gender, last_modified) " + 
    "VALUES (#{username}, #{password}, #{email}, #{full_name}, #{dob}, #{gender}, #{last_modified})") 
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void createUser(User user);

  @Select("SELECT COUNT(*) FROM users WHERE email = #{email} OR username = #{username}")
  boolean existsByUsernameOrEmail(@Param("email") String email, @Param("username") String username);

  @Update("UPDATE users SET last_login = #{last_login} WHERE id = #{id}")
  void updateLastLogin(@Param("id") Integer id, @Param("last_login") LocalDateTime lastLogin);

}

