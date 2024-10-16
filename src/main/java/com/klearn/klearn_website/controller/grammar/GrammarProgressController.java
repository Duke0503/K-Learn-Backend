package com.klearn.klearn_website.controller.grammar;

import com.klearn.klearn_website.model.GrammarProgress;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.grammar.GrammarProgressService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grammar_progress")
public class GrammarProgressController {
  private final GrammarProgressService grammarProgressService;
  private final UserService userService;

  @GetMapping("/{courseId}")
  public List<GrammarProgress> getGrammarProgress(@PathVariable Integer courseId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    String username = authentication.getName();

    User user = userService.getUser(username);  

    return grammarProgressService.getGrammarProgressByUserIdAndCourseId(user.getId(), courseId);    
  }

  @PatchMapping("/mark_learned_theory/{grammarId}/{courseId}")
  public ResponseEntity<String> markGrammarTheoryAsLearned(
          @PathVariable Integer grammarId,
          @PathVariable Integer courseId) {
      try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        String username = authentication.getName();
    
        User user = userService.getUser(username);  

        grammarProgressService.markGrammarTheoryAsLearned(user.getId(), grammarId, courseId);

        return ResponseEntity.ok("Grammar Theory marked as learned.");

      } catch (RuntimeException e) {
          return ResponseEntity.status(404).body(e.getMessage());
      }
  }

  @PatchMapping("/mark_grammar_quiz/{grammarId}/{courseId}")
  public ResponseEntity<String> markGrammarQuizAsFinished(
          @PathVariable Integer grammarId,
          @PathVariable Integer courseId) {
      try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        String username = authentication.getName();
    
        User user = userService.getUser(username);  

        grammarProgressService.markGrammarQuizAsFinished(user.getId(), grammarId, courseId);

        return ResponseEntity.ok("Grammar Quiz marked as finished.");

      } catch (RuntimeException e) {
          return ResponseEntity.status(404).body(e.getMessage());
      }
  }

  @GetMapping("/count_learned/{courseId}")
  public ResponseEntity<Integer> countLearnedGrammar(
          @PathVariable Integer courseId) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      String username = authentication.getName();
  
      User user = userService.getUser(username); 

      int count = grammarProgressService.countLearnedGrammar(user.getId(), courseId);

      return ResponseEntity.ok(count);
  }

  @GetMapping("/count_not_learned/{courseId}")
  public ResponseEntity<Integer> countNotLearnedGrammar(
          @PathVariable Integer courseId) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      String username = authentication.getName();
  
      User user = userService.getUser(username); 

      int count = grammarProgressService.countNotLearnedGrammar(user.getId(), courseId);

      return ResponseEntity.ok(count);
  }
}
