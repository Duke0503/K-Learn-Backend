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

    /**
     * Get the grammar progress for a specified course.
     *
     * @param courseId The ID of the course.
     * @return A list of GrammarProgress entries for the user and course.
     */
    @GetMapping("/{courseId}")
    public List<GrammarProgress> getGrammarProgress(@PathVariable Integer courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUser(username).orElseThrow(() -> new RuntimeException("User not found"));
        return grammarProgressService.getGrammarProgressByUserIdAndCourseId(user.getId(), courseId);
    }

    /**
     * Mark the grammar theory as learned for a specified grammar and course.
     *
     * @param grammarId The ID of the grammar entry.
     * @param courseId  The ID of the course.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PatchMapping("/mark_learned_theory/{grammarId}/{courseId}")
    public ResponseEntity<String> markGrammarTheoryAsLearned(
            @PathVariable Integer grammarId,
            @PathVariable Integer courseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUser(username).orElseThrow(() -> new RuntimeException("User not found"));
            grammarProgressService.markGrammarTheoryAsLearned(user.getId(), grammarId, courseId);
            return ResponseEntity.ok("Grammar Theory marked as learned.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Mark the grammar quiz as finished for a specified grammar and course.
     *
     * @param grammarId The ID of the grammar entry.
     * @param courseId  The ID of the course.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PatchMapping("/mark_grammar_quiz/{grammarId}/{courseId}")
    public ResponseEntity<String> markGrammarQuizAsFinished(
            @PathVariable Integer grammarId,
            @PathVariable Integer courseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUser(username).orElseThrow(() -> new RuntimeException("User not found"));
            grammarProgressService.markGrammarQuizAsFinished(user.getId(), grammarId, courseId);
            return ResponseEntity.ok("Grammar Quiz marked as finished.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Count the number of learned grammar entries for a specified course.
     *
     * @param courseId The ID of the course.
     * @return ResponseEntity containing the count of learned grammar entries.
     */
    @GetMapping("/count_learned/{courseId}")
    public ResponseEntity<Integer> countLearnedGrammar(@PathVariable Integer courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUser(username).orElseThrow(() -> new RuntimeException("User not found"));
        int count = grammarProgressService.countLearnedGrammar(user.getId(), courseId);
        return ResponseEntity.ok(count);
    }

    /**
     * Count the number of not learned grammar entries for a specified course.
     *
     * @param courseId The ID of the course.
     * @return ResponseEntity containing the count of not learned grammar entries.
     */
    @GetMapping("/count_not_learned/{courseId}")
    public ResponseEntity<Integer> countNotLearnedGrammar(@PathVariable Integer courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUser(username).orElseThrow(() -> new RuntimeException("User not found"));
        int count = grammarProgressService.countNotLearnedGrammar(user.getId(), courseId);
        return ResponseEntity.ok(count);
    }

    /**
     * Update an existing GrammarProgress entry.
     *
     * @param grammarProgress The GrammarProgress entry with updated details.
     * @return ResponseEntity indicating the result of the update operation.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateGrammarProgress(@RequestBody GrammarProgress grammarProgress) {
        try {
            grammarProgressService.updateGrammarProgress(grammarProgress);
            return ResponseEntity.ok("Grammar Progress updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Soft delete a GrammarProgress entry for a specified user, grammar, and
     * course.
     *
     * @param userId    The ID of the user.
     * @param grammarId The ID of the grammar entry.
     * @param courseId  The ID of the course.
     * @return ResponseEntity indicating the result of the soft delete operation.
     */
    @DeleteMapping("/soft_delete/{userId}/{grammarId}/{courseId}")
    public ResponseEntity<String> softDeleteGrammarProgress(
            @PathVariable Integer userId,
            @PathVariable Integer grammarId,
            @PathVariable Integer courseId) {
        try {
            grammarProgressService.softDeleteGrammarProgress(userId, grammarId, courseId);
            return ResponseEntity.ok("Grammar Progress soft deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Permanently delete a GrammarProgress entry for a specified user, grammar, and
     * course.
     *
     * @param userId    The ID of the user.
     * @param grammarId The ID of the grammar entry.
     * @param courseId  The ID of the course.
     * @return ResponseEntity indicating the result of the permanent delete
     *         operation.
     */
    @DeleteMapping("/delete/{userId}/{grammarId}/{courseId}")
    public ResponseEntity<String> deleteGrammarProgressPermanently(
            @PathVariable Integer userId,
            @PathVariable Integer grammarId,
            @PathVariable Integer courseId) {
        try {
            grammarProgressService.deleteGrammarProgressPermanently(userId, grammarId, courseId);
            return ResponseEntity.ok("Grammar Progress permanently deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Get all active GrammarProgress entries.
     *
     * @return A list of all active GrammarProgress entries.
     */
    @GetMapping("/all_active")
    public List<GrammarProgress> getAllActiveGrammarProgress() {
        return grammarProgressService.getAllActiveGrammarProgress();
    }
}
