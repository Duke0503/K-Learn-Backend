package com.klearn.klearn_website.controller.course;

import com.klearn.klearn_website.dto.dtoin.CourseDTOIn;
import com.klearn.klearn_website.dto.dtoout.CourseWithCountDTOOut;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.course.CourseService;
import com.klearn.klearn_website.service.course.MyCourseService;
import com.klearn.klearn_website.service.user.UserService;

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
    private final UserService userService;
    private final MyCourseService myCourseService;

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
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }
        try {
            courseService.createCourse(courseDTOIn);
            return new ResponseEntity<>("Course created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to create course: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param courseId The ID of the course to retrieve.
     * @return ResponseEntity containing the course details or an error message if
     *         not found.
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

    /**
     * Updates an existing course by ID.
     *
     * @param courseId    The ID of the course to update.
     * @param courseDTOIn The DTO containing the new course details.
     * @return ResponseEntity containing the result of the update operation.
     */
    @PutMapping("/update/{courseId}")
    public ResponseEntity<?> updateCourseById(@PathVariable Integer courseId,
            @RequestBody @Valid CourseDTOIn courseDTOIn) {
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }

        try {
            Course updatedCourse = courseService.updateCourse(courseId, courseDTOIn);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Course not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes multiple courses by their IDs.
     *
     * @param courseIds List of course IDs to delete.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCourses(@RequestBody List<Integer> courseIds) {
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to delete courses.",
                    HttpStatus.FORBIDDEN);
        }

        StringBuilder responseMessage = new StringBuilder();
        boolean allSuccess = true;

        for (Integer courseId : courseIds) {
            try {
                courseService.deleteCourse(courseId);
                responseMessage.append("Course ID ").append(courseId).append(" deleted successfully. ");
            } catch (IllegalArgumentException e) {
                responseMessage.append("Course ID ").append(courseId).append(" not found. ");
                allSuccess = false;
            } catch (Exception e) {
                responseMessage.append("Error deleting course ID ").append(courseId).append(": ").append(e.getMessage())
                        .append(" ");
                allSuccess = false;
            }
        }

        return new ResponseEntity<>(responseMessage.toString(),
                allSuccess ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT);
    }

    @GetMapping("/get-course-with-user-count")
    public ResponseEntity<?> getCourseWithUserCount() {
        User user = userService.getAuthenticatedUser();

        // Allow access only if the user has role 1 or role 2
        if (user.getRole() != 1 && user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN);
        }

        // Retrieve the list of courses with user count
        List<CourseWithCountDTOOut> courseWithCountList = myCourseService.getCourseWithUserCount();

        // Return the list with an OK status
        return new ResponseEntity<>(courseWithCountList, HttpStatus.OK);
    }
}
