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

    private VocabularyQuizService vocabularyQuizService;
    private UserService userService;

    @GetMapping("/vocabulary/{topicId}")
    public ResponseEntity<List<VocabularyQuestionDTOOut>> createVocabularyQuiz(@PathVariable Integer topicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.getUser(username);

        List<VocabularyQuestionDTOOut> listQuestions = vocabularyQuizService.createVocabularyQuiz(user.getId(),
                topicId);

        return ResponseEntity.ok(listQuestions);
    }
}
