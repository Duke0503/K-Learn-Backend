package com.klearn.klearn_website.controller.quiz;

import com.klearn.klearn_website.dto.dtoout.VocabularyQuestionDTOOut;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.quiz.ComprehensiveQuizService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comprehensive_quiz")
public class ComprehensiveQuizController {
    private final ComprehensiveQuizService comprehensiveQuizService;
    private final UserService userService;

    /**
     * Retrieves a vocabulary comprehensive quiz for the specified user and course.
     *
     * @param userId   The ID of the user.
     * @param courseId The ID of the course.
     * @return ResponseEntity containing a list of quiz questions.
     */
    @GetMapping("/vocabulary/{courseId}")
    public ResponseEntity<List<VocabularyQuestionDTOOut>> getVocabComprehensiveQuiz(
            @PathVariable Integer courseId) {
        
        User user = userService.getAuthenticatedUser();

        // Call the service to get the quiz questions
        List<VocabularyQuestionDTOOut> quizQuestions = comprehensiveQuizService.getVocabComprehensiveQuiz(user.getId(), courseId);
        
        // Return the questions in the response
        return ResponseEntity.ok(quizQuestions);
    }
}
