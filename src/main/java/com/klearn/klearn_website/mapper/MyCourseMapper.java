package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.MyCourse;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface MyCourseMapper {

    @Insert("INSERT INTO my_course (user_id, course_id, date_registration, payment_status, last_modified, is_deleted)" +
            "VALUES (#{id.user_id}, #{id.course_id}, #{date_registration}, #{payment_status}, #{last_modified}, #{is_deleted})")
    void insertMyCourse(MyCourse myCourse);

    @Select("SELECT mc.user_id, mc.course_id, mc.payment_status, mc.is_deleted, " +
            "c.id AS course_id, c.course_name, c.course_level, c.course_image, " +
            "u.id AS user_id, u.username " +
            "FROM my_course mc " +
            "JOIN courses c ON mc.course_id = c.id " +
            "JOIN users u ON mc.user_id = u.id " +
            "WHERE mc.user_id = #{user_id} AND mc.is_deleted = 0")
    @Results({
            @Result(property = "id.user_id", column = "user_id"),
            @Result(property = "id.course_id", column = "course_id"),
            @Result(property = "payment_status", column = "payment_status"),
            @Result(property = "is_deleted", column = "is_deleted"),

            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_image", column = "course_image"),

            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.username", column = "username")
    })
    List<MyCourse> getMyCourseByUserId(@Param("user_id") Integer userId);

    @Select("SELECT mc.user_id, mc.course_id, mc.payment_status, mc.is_deleted, " +
            "c.id AS course_id, c.course_name, c.course_level, c.course_image, " +
            "u.id AS user_id, u.username " +
            "FROM my_course mc " +
            "JOIN courses c ON mc.course_id = c.id " +
            "JOIN users u ON mc.user_id = u.id " +
            "WHERE mc.user_id = #{user_id} AND mc.course_id = #{course_id} AND mc.is_deleted = 0")
    @Results({
            @Result(property = "id.user_id", column = "user_id"),
            @Result(property = "id.course_id", column = "course_id"),
            @Result(property = "payment_status", column = "payment_status"),
            @Result(property = "is_deleted", column = "is_deleted"),

            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_image", column = "course_image"),

            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.username", column = "username")
    })
    MyCourse getMyCourseByUserIdAndCourseId(@Param("user_id") Integer userId, @Param("course_id") Integer courseId);

    @Select("SELECT COUNT(*) > 0 " +
            "FROM my_course mc " +
            "WHERE mc.user_id = #{user_id} AND mc.course_id = #{course_id} AND mc.is_deleted = 0")
    boolean existsMyCourseByUserIdAndCourseId(@Param("user_id") Integer userId, @Param("course_id") Integer courseId);
}
