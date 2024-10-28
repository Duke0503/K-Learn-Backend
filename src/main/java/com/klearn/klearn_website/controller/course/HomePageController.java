package com.klearn.klearn_website.controller.course;

import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.course.CourseHomePageService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/homepage")
public class HomePageController {

    private final CourseHomePageService courseHomePageService;
    private final UserService userService;

    /**
     * Retrieves the main page data for the given user ID.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing the main page data or an error message if
     *         any issue occurs.
     */
    @GetMapping("/topic-section")
    public ResponseEntity<String> getTopicSection() {
        try {
            String topicSection = courseHomePageService.getTopicSection();
            return new ResponseEntity<>(topicSection, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching topic section data: " + e.getMessage());
        }
    }

    /**
     * Retrieves the progress section for the authenticated user.
     *
     * @return ResponseEntity containing the progress section data or an error
     *         message if the user is not authenticated.
     */
    @GetMapping("/progress-section")
    public ResponseEntity<String> getProgressSection() {
        User user = userService.getAuthenticatedUser();

        String progressSection = courseHomePageService.getProgressSection(user.getId());

        return new ResponseEntity<>(progressSection, HttpStatus.OK);
    }
}
