package com.klearn.klearn_website.controller.quiz;

import com.klearn.klearn_website.dto.dtoin.GrammarQuizSubmissionDTOIn;
import com.klearn.klearn_website.dto.dtoin.VocabularyQuizSubmissionDTOIn;
import com.klearn.klearn_website.dto.dtoout.GrammarAnswerDTOOut;
import com.klearn.klearn_website.dto.dtoout.VocabularyAnswerDTOOut;
import com.klearn.klearn_website.model.ComprehensiveTestResults;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.quiz.ComprehensiveTestResultsService;
import com.klearn.klearn_website.service.user.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comprehensive-test-results")
public class ComprehensiveTestResultsController {

    private final ComprehensiveTestResultsService comprehensiveTestResultsService;
    private final UserService userService;

    // Endpoint to get the most recent comprehensive test result by course_id,
    // user_id, and test_type
    @GetMapping("/vocabulary/{courseId}")
    public ResponseEntity<ComprehensiveTestResults> getMostRecentVocabTest(@PathVariable Integer courseId) {
        User user = userService.getAuthenticatedUser();

        ComprehensiveTestResults result = comprehensiveTestResultsService.getMostRecentTestByCourseUserAndType(courseId,
                user.getId(), "vocabulary");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/grammar/{courseId}")
    public ResponseEntity<ComprehensiveTestResults> getMostRecentGrammarTest(@PathVariable Integer courseId) {
        User user = userService.getAuthenticatedUser();

        ComprehensiveTestResults result = comprehensiveTestResultsService.getMostRecentTestByCourseUserAndType(courseId,
                user.getId(), "grammar");

        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit-grammar-quiz-answers")
    public ResponseEntity<String> submitGrammarQuizAnswers(
            @Valid @RequestBody GrammarQuizSubmissionDTOIn submissionDTO) {
        User user = userService.getAuthenticatedUser();
        comprehensiveTestResultsService.processGrammarQuizAnswers(
                submissionDTO.getAnswers(),
                submissionDTO.getCourse_id(),
                user.getId());

        return ResponseEntity.ok("Grammar quiz answers submitted successfully");
    }

    @GetMapping("/grammar-answers/{testId}")
    public ResponseEntity<List<GrammarAnswerDTOOut>> getGrammarAnswersByTestId(
            @PathVariable Integer testId) {
        User user = userService.getAuthenticatedUser();
        
        List<GrammarAnswerDTOOut> answers = comprehensiveTestResultsService.getGrammarAnswerByTestId(testId);
        return ResponseEntity.ok(answers);
    }

    @PostMapping("/submit-vocabulary-quiz-answers")
    public ResponseEntity<String> submitVocabularyQuizAnswers(
            @Valid @RequestBody VocabularyQuizSubmissionDTOIn submissionDTO) {
        User user = userService.getAuthenticatedUser();
        comprehensiveTestResultsService.processVocabularyQuizAnswers(
                submissionDTO.getAnswers(),
                submissionDTO.getCourse_id(),
                user.getId());

        return ResponseEntity.ok("Grammar quiz answers submitted successfully");
    }

    @GetMapping("/vocabulary-answers/{testId}")
    public ResponseEntity<List<VocabularyAnswerDTOOut>> getVocabularyAnswersByTestId(
            @PathVariable Integer testId) {
        User user = userService.getAuthenticatedUser();
        
        List<VocabularyAnswerDTOOut> answers = comprehensiveTestResultsService.getVocabularyAnswerByTestId(testId);
        return ResponseEntity.ok(answers);
    }
}