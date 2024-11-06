package com.klearn.klearn_website.controller.quiz;

import com.klearn.klearn_website.model.ComprehensiveTestResults;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.quiz.ComprehensiveTestResultsService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comprehensive-test-results")
public class ComprehensiveTestResultsController {

    private final ComprehensiveTestResultsService comprehensiveTestResultsService;
    private final UserService userService;

    // Endpoint to get the most recent comprehensive test result by course_id, user_id, and test_type
    @GetMapping("/vocabulary/{courseId}")
    public ResponseEntity<ComprehensiveTestResults> getMostRecentVocabTest(@PathVariable Integer courseId) {
        User user = userService.getAuthenticatedUser();

        ComprehensiveTestResults result = comprehensiveTestResultsService.getMostRecentTestByCourseUserAndType(courseId, user.getId(), "vocabulary");
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/grammar/{courseId}")
    public ResponseEntity<ComprehensiveTestResults> getMostRecentGrammarTest(@PathVariable Integer courseId) {
        User user = userService.getAuthenticatedUser();

        ComprehensiveTestResults result = comprehensiveTestResultsService.getMostRecentTestByCourseUserAndType(courseId, user.getId(), "grammar");
        
        return ResponseEntity.ok(result);
    }
}