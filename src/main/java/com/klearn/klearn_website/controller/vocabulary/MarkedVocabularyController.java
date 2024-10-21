package com.klearn.klearn_website.controller.vocabulary;

import com.klearn.klearn_website.model.MarkedVocabulary;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.service.vocabulary.MarkedVocabularyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/marked_vocabulary")
public class MarkedVocabularyController {
    private final MarkedVocabularyService markedVocabularyService;
    private final UserService userService;

    /**
     * Creates a new marked vocabulary entry for the current user.
     *
     * @param vocabularyId The ID of the vocabulary to mark.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/marked/{vocabularyId}")
    public ResponseEntity<String> createMarkedVocabulary(@PathVariable Integer vocabularyId) {
        User user = userService.getAuthenticatedUser();

        markedVocabularyService.createMarkedVocabByUserAndVocabulary(user.getId(), vocabularyId);
        return ResponseEntity.ok("Vocabulary marked successfully.");
    }

    /**
     * Retrieves all marked vocabularies for the current user.
     *
     * @return List of MarkedVocabulary entries.
     */
    @GetMapping("/list")
    public List<MarkedVocabulary> getMarkedVocabularies() {
        User user = userService.getAuthenticatedUser();

        return markedVocabularyService.getListMarkedVocabularybyUserId(user.getId());
    }

    /**
     * Soft deletes a marked vocabulary by vocabulary ID.
     *
     * @param vocabularyId The ID of the marked vocabulary to delete.
     * @return ResponseEntity indicating the result of the deletion.
     */
    @DeleteMapping("/soft_delete/{vocabularyId}")
    public ResponseEntity<String> softDeleteMarkedVocabulary(@PathVariable Integer vocabularyId) {
        markedVocabularyService.softDelete(vocabularyId);
        return ResponseEntity.ok("Marked vocabulary soft deleted successfully.");
    }
}
