package com.klearn.klearn_website.controller.course;

import com.klearn.klearn_website.service.course.CourseHomePageService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/homepage")
public class HomePageController {

    private final CourseHomePageService courseHomePageService;

    /**
     * Retrieves the main page data for the given user ID.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing the main page data or an error message if any issue occurs.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getDataMainPage(@PathVariable Integer userId) {
        try {
            String dataMainPage = courseHomePageService.getDataHomePage(userId);
            return new ResponseEntity<>(dataMainPage, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error fetching data for user ID: " + userId + ". " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

