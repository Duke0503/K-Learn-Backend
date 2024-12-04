package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.PasswordResetToken;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper
public interface PasswordResetTokenMapper {

    // Insert a new password reset token
    @Insert("INSERT INTO password_reset_tokens (token, user_id, expiry_date) " +
            "VALUES (#{token}, #{user.id}, #{expiryDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createToken(PasswordResetToken passwordResetToken);

    // Find a token by its value and user ID
    @Select("SELECT prt.id, prt.token, prt.expiry_date, " +
            "u.id AS user_id, u.username, u.email, u.fullname, u.dob, u.avatar, u.gender, " +
            "u.last_login, u.last_modified, u.is_deleted, u.role, u.type " +
            "FROM password_reset_tokens prt " +
            "JOIN users u ON prt.user_id = u.id " +
            "WHERE prt.token = #{token} AND prt.user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "token", column = "token"),
            @Result(property = "expiryDate", column = "expiry_date"),
            // Mapping User properties
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.username", column = "username"),
            @Result(property = "user.email", column = "email"),
            @Result(property = "user.fullname", column = "fullname"),
            @Result(property = "user.dob", column = "dob"),
            @Result(property = "user.avatar", column = "avatar"),
            @Result(property = "user.gender", column = "gender"),
            @Result(property = "user.last_login", column = "last_login"),
            @Result(property = "user.last_modified", column = "last_modified"),
            @Result(property = "user.is_deleted", column = "is_deleted"),
            @Result(property = "user.role", column = "role"),
            @Result(property = "user.type", column = "type")
    })
    Optional<PasswordResetToken> findByTokenAndUserId(@Param("token") String token, @Param("userId") Integer userId);

    @Select("SELECT prt.id, prt.token, prt.expiry_date, " +
            "u.id AS user_id, u.username, u.email, u.fullname, u.dob, u.avatar, u.gender, " +
            "u.last_login, u.last_modified, u.is_deleted, u.role, u.type " +
            "FROM password_reset_tokens prt " +
            "JOIN users u ON prt.user_id = u.id " +
            "WHERE prt.user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "token", column = "token"),
            @Result(property = "expiryDate", column = "expiry_date"),
            // Mapping User properties
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.username", column = "username"),
            @Result(property = "user.email", column = "email"),
            @Result(property = "user.fullname", column = "fullname"),
            @Result(property = "user.dob", column = "dob"),
            @Result(property = "user.avatar", column = "avatar"),
            @Result(property = "user.gender", column = "gender"),
            @Result(property = "user.last_login", column = "last_login"),
            @Result(property = "user.last_modified", column = "last_modified"),
            @Result(property = "user.is_deleted", column = "is_deleted"),
            @Result(property = "user.role", column = "role"),
            @Result(property = "user.type", column = "type")
    })
    Optional<PasswordResetToken> findByUserId(@Param("userId") Integer userId);

    // Update an existing token with a new token and expiry date using a
    // PasswordResetToken object
    @Update("UPDATE password_reset_tokens SET token = #{token}, expiry_date = #{expiryDate} WHERE user_id = #{user.id}")
    void updateToken(PasswordResetToken passwordResetToken);

    // Delete a token by its ID
    @Delete("DELETE FROM password_reset_tokens WHERE id = #{id}")
    void deleteToken(@Param("id") Integer id);

    // Delete all expired tokens
    @Delete("DELETE FROM password_reset_tokens WHERE expiry_date < #{currentDate}")
    void deleteExpiredTokens(@Param("currentDate") LocalDateTime currentDate);
}
