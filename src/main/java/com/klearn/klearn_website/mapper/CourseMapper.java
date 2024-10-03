package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.Course;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CourseMapper {

  // Get all courses
  @Select("SELECT * FROM courses WHERE is_deleted = 0")
  List<Course> findAll();
  
  // Get course by id
  @Select("SELECT * FROM courses WHERE id = #{course_id} AND is_deleted = 0")
  Course findCourseById(@Param("course_id") Integer course_id);

  // Insert a course
  @Insert("INSERT INTO courses (course_name, course_level, course_description, course_image, course_price, created_at, last_modified, is_deleted) " +
            "VALUES (#{course_name}, #{course_level}, #{course_description}, #{course_image}, #{course_price}, #{created_at}, #{last_modified}, #{is_deleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void createCourse(Course course);
}
