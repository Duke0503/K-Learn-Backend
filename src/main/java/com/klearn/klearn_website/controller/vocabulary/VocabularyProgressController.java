package com.klearn.klearn_website.controller.vocabulary;

import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.VocabularyProgress;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.service.vocabulary.VocabularyProgressService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/vocabulary_progress")
public class VocabularyProgressController {
  private UserService userService;
  private VocabularyProgressService vocabularyProgressService;

@GetMapping("/topic/{topicId}")
  public List<VocabularyProgress> getVocabularyProgress(@PathVariable Integer topicId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    String username = authentication.getName();

    User user = userService.getUser(username);  

    return vocabularyProgressService.getVocabularyByUserIdAndTopicId(user.getId(), topicId);
  }
  
}
