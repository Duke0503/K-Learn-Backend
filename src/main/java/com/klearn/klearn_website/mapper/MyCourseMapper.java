package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.dto.dtoout.CourseWithCountDTOOut;
import com.klearn.klearn_website.model.MyCourse;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface MyCourseMapper {

    // Insert a new MyCourse entry
    @Insert("INSERT INTO my_course (user_id, course_id, date_registration, payment_status, last_modified, is_deleted) "
            +
            "VALUES (#{id.user_id}, #{id.course_id}, #{date_registration}, #{payment_status}, #{last_modified}, #{is_deleted})")
    void insertMyCourse(MyCourse myCourse);

    // Update an existing MyCourse entry
    @Update("UPDATE my_course SET payment_status = #{payment_status}, last_modified = #{last_modified}, is_deleted = #{is_deleted} "
            +
            "WHERE user_id = #{id.user_id} AND course_id = #{id.course_id}")
    void updateMyCourse(MyCourse myCourse);

    // Soft delete a MyCourse entry by setting is_deleted to true
    @Update("UPDATE my_course SET is_deleted = 1, last_modified = NOW() " +
            "WHERE user_id = #{user_id} AND course_id = #{course_id}")
    void softDeleteMyCourse(@Param("user_id") Integer userId, @Param("course_id") Integer courseId);

    // Permanently delete a MyCourse entry
    @Delete("DELETE FROM my_course WHERE user_id = #{user_id} AND course_id = #{course_id}")
    void deleteMyCoursePermanently(@Param("user_id") Integer userId, @Param("course_id") Integer courseId);

    // Get all MyCourse entries by user ID
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

    // Get a MyCourse entry by user ID and course ID
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

    // Check if a MyCourse entry exists by user ID and course ID
    @Select("SELECT COUNT(*) > 0 " +
            "FROM my_course mc " +
            "WHERE mc.user_id = #{user_id} AND mc.course_id = #{course_id} AND mc.is_deleted = 0")
    boolean existsMyCourseByUserIdAndCourseId(@Param("user_id") Integer userId, @Param("course_id") Integer courseId);

    // Get all active MyCourse entries
    @Select("SELECT mc.user_id, mc.course_id, mc.payment_status, mc.is_deleted, " +
            "c.id AS course_id, c.course_name, c.course_level, c.course_image, " +
            "u.id AS user_id, u.username " +
            "FROM my_course mc " +
            "JOIN courses c ON mc.course_id = c.id " +
            "JOIN users u ON mc.user_id = u.id " +
            "WHERE mc.is_deleted = 0")
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
    List<MyCourse> getAllActiveMyCourses();

    @Select("SELECT mc.course_id, COUNT(mc.user_id) AS user_count, " +
            "c.course_name, c.course_level, c.course_description, c.course_image, c.course_price " +
            "FROM my_course mc " +
            "JOIN courses c ON mc.course_id = c.id " +
            "WHERE mc.is_deleted = 0 " + 
            "GROUP BY mc.course_id, c.course_name, c.course_level, c.course_description, c.course_image, c.course_price")
    @Results({
            @Result(property = "course_id", column = "course_id"),
            @Result(property = "course_name", column = "course_name"),
            @Result(property = "course_level", column = "course_level"),
            @Result(property = "course_description", column = "course_description"),
            @Result(property = "course_image", column = "course_image"),
            @Result(property = "course_price", column = "course_price"),
            @Result(property = "user_count", column = "user_count")
    })
    List<CourseWithCountDTOOut> getCourseWithUserCount();
}
