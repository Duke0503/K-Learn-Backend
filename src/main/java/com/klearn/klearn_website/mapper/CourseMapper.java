package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CourseMapper {

    // Get all courses that are not deleted
    @Select("SELECT * FROM courses WHERE is_deleted = 0")
    List<Course> findAll();

    // Get course by id (use Optional to handle absence of a course)
    @Select("SELECT * FROM courses WHERE id = #{courseId} AND is_deleted = 0")
    Optional<Course> findCourseById(@Param("courseId") Integer courseId);

    // Insert a new course
    @Insert("INSERT INTO courses (course_name, course_level, course_description, course_image, course_price, created_at, last_modified, is_deleted) "
            +
            "VALUES (#{course_name}, #{course_level}, #{course_description}, #{course_image}, #{course_price}, #{created_at}, #{last_modified}, #{is_deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createCourse(Course course);

    // Update course details
    @Update("UPDATE courses SET course_name = #{course_name}, course_level = #{course_level}, course_description = #{course_description}, "
            +
            "course_image = #{course_image}, course_price = #{course_price}, last_modified = NOW() WHERE id = #{id} AND is_deleted = 0")
    void updateCourse(Course course);

    // Soft delete a course by setting is_deleted = 1
    @Update("UPDATE courses SET is_deleted = 1, last_modified = NOW() WHERE id = #{courseId}")
    void softDeleteCourse(@Param("courseId") Integer courseId);
}
