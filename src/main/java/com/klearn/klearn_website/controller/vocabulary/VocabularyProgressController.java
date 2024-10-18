package com.klearn.klearn_website.controller.vocabulary;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.VocabularyProgress;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.service.vocabulary.VocabularyProgressService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PatchMapping("/mark/topic/{topicId}/vocabulary/{vocabularyId}")
    public ResponseEntity<String> markVocabularyAsLearned(@PathVariable Integer topicId,
            @PathVariable Integer vocabularyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = userService.getUser(username);

        vocabularyProgressService.markVocabularyAsLearned(user.getId(), topicId, vocabularyId);
        return ResponseEntity.ok("Vocabulary marked as learned.");
    }

    @GetMapping("/progress/{topicId}")
    public ResponseEntity<?> getVocabularyProgressCounts(@PathVariable Integer topicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.getUser(username);

        Integer countVocabularyNotLearned = vocabularyProgressService.countVocabularyNotLearned(user.getId(), topicId);
        Integer countVocabularyLearned = vocabularyProgressService.countVocabularyLearned(user.getId(), topicId);

        Map<String, Integer> response = new HashMap<>();
        response.put("countVocabularyNotLearned", countVocabularyNotLearned);
        response.put("countVocabularyLearned", countVocabularyLearned);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/learned/{topicId}/")
    public List<VocabularyProgress> getLearnedVocabularyProgress(@PathVariable Integer topicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.getUser(username);

        return vocabularyProgressService.getVocabularyLearned(user.getId(), topicId);
    }

    @GetMapping("/not_learned/{topicId}/")
    public List<VocabularyProgress> getNotLearnedVocabularyProgress(@PathVariable Integer topicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.getUser(username);

        return vocabularyProgressService.getVocabularyNotLearned(user.getId(), topicId);
    }
}
