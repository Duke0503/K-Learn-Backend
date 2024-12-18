package com.klearn.klearn_website.controller.vocabulary;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.VocabularyProgress;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.service.vocabulary.VocabularyProgressService;

import jakarta.validation.constraints.Positive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/vocabulary_progress")
public class VocabularyProgressController {
    private final UserService userService;
    private final VocabularyProgressService vocabularyProgressService;

    /**
     * Retrieves vocabulary progress for a specific topic by user.
     *
     * @param topicId The ID of the topic.
     * @return List of VocabularyProgress entries for the user and topic.
     */
    @GetMapping("/topic/{topicId}")
    public List<VocabularyProgress> getVocabularyProgress(@PathVariable @Positive Integer topicId) {
        User user = userService.getAuthenticatedUser();
                
        return vocabularyProgressService.getVocabularyProgressByUserIdAndTopicId(user.getId(), topicId);
    }

    /**
     * Marks a vocabulary as learned for a specific topic.
     *
     * @param topicId      The ID of the topic.
     * @param vocabularyId The ID of the vocabulary to mark as learned.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PatchMapping("/mark/topic/{topicId}/vocabulary/{vocabularyId}")
    public ResponseEntity<String> markVocabularyAsLearned(@PathVariable @Positive Integer topicId,
            @PathVariable Integer vocabularyId) {
        User user = userService.getAuthenticatedUser();

        vocabularyProgressService.markVocabularyAsLearned(user.getId(), topicId, vocabularyId);
        return ResponseEntity.ok("Vocabulary marked as learned.");
    }

        /**
     * Marks a vocabulary as proficient for a specific topic.
     *
     * @param topicId      The ID of the topic.
     * @param vocabularyId The ID of the vocabulary to mark as learned.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PatchMapping("/mark-proficient/topic/{topicId}/vocabulary/{vocabularyId}")
    public ResponseEntity<String> markVocabularyAsProficient(@PathVariable @Positive Integer topicId,
            @PathVariable Integer vocabularyId) {
        User user = userService.getAuthenticatedUser();

        vocabularyProgressService.markVocabularyAsProficient(user.getId(), topicId, vocabularyId);
        return ResponseEntity.ok("Vocabulary marked as proficient.");
    }

            /**
     * Marks a vocabulary as proficient for a specific topic.
     *
     * @param topicId      The ID of the topic.
     * @param vocabularyId The ID of the vocabulary to mark as learned.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PatchMapping("/mark-not-proficient/topic/{topicId}/vocabulary/{vocabularyId}")
    public ResponseEntity<String> markVocabularyAsNotProficient(@PathVariable @Positive Integer topicId,
            @PathVariable Integer vocabularyId) {
        User user = userService.getAuthenticatedUser();

        vocabularyProgressService.markVocabularyAsNotProficient(user.getId(), topicId, vocabularyId);
        return ResponseEntity.ok("Vocabulary marked as not proficient.");
    }

    /**
     * Retrieves the count of learned and not learned vocabularies for a specific
     * topic.
     *
     * @param topicId The ID of the topic.
     * @return ResponseEntity containing the counts of learned and not learned
     *         vocabularies.
     */
    @GetMapping("/progress/{topicId}")
    public ResponseEntity<?> getVocabularyProgressCounts(@PathVariable @Positive Integer topicId) {
        User user = userService.getAuthenticatedUser();

        Integer countVocabularyNotLearned = vocabularyProgressService.countVocabularyNotLearned(user.getId(), topicId);
        Integer countVocabularyLearned = vocabularyProgressService.countVocabularyLearned(user.getId(), topicId);

        Map<String, Integer> response = new HashMap<>();
        response.put("countVocabularyNotLearned", countVocabularyNotLearned);
        response.put("countVocabularyLearned", countVocabularyLearned);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all learned vocabulary progress for a specific topic.
     *
     * @param topicId The ID of the topic.
     * @return List of learned VocabularyProgress entries for the user and topic.
     */
    @GetMapping("/learned/{topicId}")
    public List<VocabularyProgress> getLearnedVocabularyProgress(@PathVariable @Positive Integer topicId) {
        User user = userService.getAuthenticatedUser();

        return vocabularyProgressService.getVocabularyProgressByUserIdAndTopicId(user.getId(), topicId).stream()
                .filter(VocabularyProgress::getIs_learned)
                .toList();
    }

    /**
     * Retrieves all not learned vocabulary progress for a specific topic.
     *
     * @param topicId The ID of the topic.
     * @return List of not learned VocabularyProgress entries for the user and
     *         topic.
     */
    @GetMapping("/not_learned/{topicId}")
    public List<VocabularyProgress> getNotLearnedVocabularyProgress(@PathVariable @Positive Integer topicId) {
        User user = userService.getAuthenticatedUser();

        return vocabularyProgressService.getVocabularyProgressByUserIdAndTopicId(user.getId(), topicId).stream()
                .filter(vp -> !vp.getIs_learned())
                .toList();
    }

    /**
     * Creates a new VocabularyProgress entry.
     *
     * @param vocabularyProgress The VocabularyProgress entity to create.
     * @return ResponseEntity indicating the result of the create operation.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createVocabularyProgress(@RequestBody VocabularyProgress vocabularyProgress) {
        vocabularyProgressService.createVocabularyProgress(vocabularyProgress);
        return ResponseEntity.ok("VocabularyProgress created successfully.");
    }

    /**
     * Updates an existing VocabularyProgress entry.
     *
     * @param vocabularyProgress The VocabularyProgress entity to update.
     * @return ResponseEntity indicating the result of the update operation.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateVocabularyProgress(@RequestBody VocabularyProgress vocabularyProgress) {
        vocabularyProgressService.updateVocabularyProgress(vocabularyProgress);
        return ResponseEntity.ok("VocabularyProgress updated successfully.");
    }

    /**
     * Soft deletes a VocabularyProgress entry.
     *
     * @param userId       The ID of the user.
     * @param vocabularyId The ID of the vocabulary.
     * @param topicId      The ID of the topic.
     * @return ResponseEntity indicating the result of the soft delete operation.
     */
    @DeleteMapping("/soft_delete/{userId}/{vocabularyId}/{topicId}")
    public ResponseEntity<String> softDeleteVocabularyProgress(@PathVariable @Positive Integer userId,
            @PathVariable @Positive Integer vocabularyId,
            @PathVariable @Positive Integer topicId) {
        vocabularyProgressService.softDeleteVocabularyProgress(userId, vocabularyId, topicId);
        return ResponseEntity.ok("VocabularyProgress soft deleted successfully.");
    }

    /**
     * Permanently deletes a VocabularyProgress entry.
     *
     * @param userId       The ID of the user.
     * @param vocabularyId The ID of the vocabulary.
     * @param topicId      The ID of the topic.
     * @return ResponseEntity indicating the result of the permanent delete
     *         operation.
     */
    @DeleteMapping("/delete/{userId}/{vocabularyId}/{topicId}")
    public ResponseEntity<String> deleteVocabularyProgressPermanently(@PathVariable @Positive Integer userId,
            @PathVariable @Positive Integer vocabularyId,
            @PathVariable @Positive Integer topicId) {
        vocabularyProgressService.deleteVocabularyProgressPermanently(userId, vocabularyId, topicId);
        return ResponseEntity.ok("VocabularyProgress permanently deleted successfully.");
    }
}
