package com.klearn.klearn_website.service.course;

import com.klearn.klearn_website.dto.course.CreateCourseDto;
import com.klearn.klearn_website.mapper.CourseMapper;
import com.klearn.klearn_website.model.Course;

import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class CourseService {

  private CourseMapper courseMapper;

  public CourseService(CourseMapper courseMapper) {
    this.courseMapper = courseMapper;
  }

  public List<Course> getAllCourses() {
    return courseMapper.findAll();
  }

  public void createCourse(CreateCourseDto createCourseDto) {
    Course course = new Course();

    course.setCourse_name(createCourseDto.getCourse_name());
    course.setCourse_level(createCourseDto.getCourse_level());
    course.setCourse_description(createCourseDto.getCourse_description());
    course.setCourse_price(createCourseDto.getCourse_price());
    course.setCreated_at(LocalDateTime.now());
    course.setLast_modified(LocalDateTime.now());
    course.setIs_deleted(false);

    courseMapper.createCourse(course);
  }
}
