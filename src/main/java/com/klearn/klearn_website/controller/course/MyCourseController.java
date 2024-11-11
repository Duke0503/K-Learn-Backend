package com.klearn.klearn_website.controller.course;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klearn.klearn_website.dto.dtoin.MyCourseDTOIn;
import com.klearn.klearn_website.dto.dtoout.CourseWithCountDTOOut;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.course.MyCourseService;
import com.klearn.klearn_website.service.user.UserService;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@RestController
@RequestMapping("/api/mycourse")
public class MyCourseController {
    private final MyCourseService myCourseService;
    private final UserService userService;

    /**
     * Get all courses for the authenticated user.
     * 
     * @return A JSON representation of the user's courses.
     */
    @GetMapping("/user/courses")
    public ResponseEntity<String> getUserCourses() {
        User user = userService.getAuthenticatedUser();

        String courses = myCourseService.getMyCourseByUserId(user.getId());
        return ResponseEntity.ok(courses);
    }

    /**
     * Get vocabulary progress for a specific course.
     * 
     * @param courseId The ID of the course.
     * @return A JSON representation of the vocabulary progress.
     */
    @GetMapping("/vocabulary/{courseId}/progress")
    public ResponseEntity<String> getVocabularyProgress(@PathVariable @Positive Integer courseId) {
        User user = userService.getAuthenticatedUser();

        String progress = myCourseService.getVocabularyProgressByUserIdAndCourseId(user.getId(), courseId);
        return ResponseEntity.ok(progress);
    }

    /**
     * Get grammar progress for a specific course.
     * 
     * @param courseId The ID of the course.
     * @return A JSON representation of the grammar progress.
     */
    @GetMapping("/grammar/{courseId}/progress")
    public ResponseEntity<String> getGrammarProgress(@PathVariable @Positive Integer courseId) {
        User user = userService.getAuthenticatedUser();

        String progress = myCourseService.getDetailedGrammarProgressByUserIdAndCourseId(user.getId(), courseId);
        return ResponseEntity.ok(progress);
    }

    /**
     * Enroll in a new course.
     * 
     * @param myCourseDTOIn The details of the course enrollment.
     * @return A message indicating the result of the enrollment.
     */
    @GetMapping("/enroll/{courseId}")
    public ResponseEntity<String> enrollInCourse(@PathVariable @Positive Integer courseId) {
        User user = userService.getAuthenticatedUser();

        myCourseService.insertMyCourse(new MyCourseDTOIn(
                user.getId(),
                courseId,
                "pending"));
        return ResponseEntity.ok("Course enrolled successfully");
    }

    /**
     * Get detailed grammar progress, including quiz questions, for a specific
     * course.
     * 
     * @param courseId The ID of the course.
     * @return A JSON representation of the detailed grammar progress.
     */
    @GetMapping("/grammar/{courseId}/detailed-progress")
    public ResponseEntity<String> getDetailedGrammarProgress(@PathVariable Integer courseId) {
        User user = userService.getAuthenticatedUser();

        String progress = myCourseService.getDetailedGrammarProgressByUserIdAndCourseId(user.getId(), courseId);
        return ResponseEntity.ok(progress);
    }

    /**
     * Get detailed vocabulary progress for a specific course.
     * 
     * @param courseId The ID of the course.
     * @return A JSON representation of the detailed vocabulary progress.
     */
    @GetMapping("/vocabulary/{courseId}/detailed-progress")
    public ResponseEntity<String> getDetailedVocabularyProgress(@PathVariable @Positive Integer courseId) {
        User user = userService.getAuthenticatedUser();

        String progress = myCourseService.getVocabularyProgressByUserIdAndCourseId(user.getId(), courseId);
        return ResponseEntity.ok(progress);
    }

    /**
     * Get the overall course progress, including grammar and vocabulary.
     *
     * @param courseId The ID of the course.
     * @return JSON representation of the course's overall progress.
     */
    @GetMapping("/{courseId}/overall-progress")
    public ResponseEntity<String> getOverallCourseProgress(@PathVariable @Positive Integer courseId) {
        User user = userService.getAuthenticatedUser();

        String progress = myCourseService.getMyCourseByUserId(user.getId());
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/payment_status/{courseId}")
    public String myCoursePaymentStatus(@PathVariable Integer courseId) {
        User user = userService.getAuthenticatedUser();

        return myCourseService.myCoursePaymentStatus(user.getId(), courseId);
    }

    @GetMapping("/get-course-with-user-count")
    public ResponseEntity<?> getCourseWithUserCount() {
        User user = userService.getAuthenticatedUser();
    
        // Allow access only if the user has role 1 or role 2
        if (user.getRole() != 1 && user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.", HttpStatus.FORBIDDEN);
        }
    
        // Retrieve the list of courses with user count
        List<CourseWithCountDTOOut> courseWithCountList = myCourseService.getCourseWithUserCount();
    
        // Return the list with an OK status
        return new ResponseEntity<>(courseWithCountList, HttpStatus.OK);
    }
    
}
