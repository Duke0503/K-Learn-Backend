package com.klearn.klearn_website.controller.course;

import com.klearn.klearn_website.dto.dtoin.CourseDTOIn;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.service.course.CourseService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    /**
     * Retrieves all courses that are not deleted.
     *
     * @return List of all available courses.
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    /**
     * Creates a new course using the provided details.
     *
     * @param courseDTOIn The DTO containing the course details to be created.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@Valid @RequestBody CourseDTOIn courseDTOIn) {
        try {
            courseService.createCourse(courseDTOIn);
            return new ResponseEntity<>("Course created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to create course: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param courseId The ID of the course to retrieve.
     * @return ResponseEntity containing the course details or an error message if not found.
     */
    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer courseId) {
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        if (courseOptional.isPresent()) {
            return new ResponseEntity<>(courseOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found with ID: " + courseId, HttpStatus.NOT_FOUND);
        }
    }


}
