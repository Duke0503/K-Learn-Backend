package com.klearn.klearn_website.controller.quiz;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.dto.dtoout.VocabularyQuestionDTOOut;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.quiz.VocabularyQuizService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/quiz")
public class VocabularyQuizController {

    private final VocabularyQuizService vocabularyQuizService;
    private final UserService userService;

    /**
     * Creates a new vocabulary quiz for a specific topic.
     *
     * @param topicId The ID of the vocabulary topic.
     * @return ResponseEntity containing the list of VocabularyQuestionDTOOut
     *         objects representing the quiz.
     */
    @GetMapping("/vocabulary/{topicId}")
    public ResponseEntity<List<VocabularyQuestionDTOOut>> createVocabularyQuiz(@PathVariable Integer topicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.getUser(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        List<VocabularyQuestionDTOOut> listQuestions = vocabularyQuizService.createVocabularyQuiz(user.getId(),
                topicId);

        return ResponseEntity.ok(listQuestions);
    }
}
