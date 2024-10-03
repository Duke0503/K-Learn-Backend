package com.klearn.klearn_website.controller.course;

import com.klearn.klearn_website.dto.course.CreateCourseDto;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.service.course.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

  private CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping
  public List<Course> getAllCourses() {
    return courseService.getAllCourses();
  }

  @PostMapping("/create")
  public ResponseEntity<String> createCourse(@RequestBody CreateCourseDto createCourseDto) {
    try {
      courseService.createCourse(createCourseDto);
      return new ResponseEntity<>("Course created successfully", HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>("Failed to create course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}