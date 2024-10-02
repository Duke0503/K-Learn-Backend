package com.klearn.klearn_website.controller.course;

import com.klearn.klearn_website.dto.course.CreateCourseDto;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.service.course.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping("/get")
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