package com.klearn.klearn_website.controller.vocabulary;

import com.klearn.klearn_website.dto.dtoin.VocabularyDTOIn;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.service.vocabulary.VocabularyService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {

    private final VocabularyService vocabularyService;
    private final UserService userService;

    /**
     * Create a new vocabulary.
     * 
     * @param vocabularyDTOIn The DTO containing vocabulary details.
     * @return ResponseEntity with a message indicating the result.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createVocabulary(@Valid @RequestBody VocabularyDTOIn vocabularyDTOIn) {
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }

        try {
            vocabularyService.createVocabulary(vocabularyDTOIn);
            return new ResponseEntity<>("Vocabulary created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create Vocabulary: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve all vocabulary by topic ID.
     * 
     * @param topicId The topic ID.
     * @return List of Vocabulary entities.
     */
    @GetMapping("/{topicId}")
    public ResponseEntity<List<Vocabulary>> getVocabularyByTopicId(@PathVariable @Positive Integer topicId) {
        List<Vocabulary> vocabularies = vocabularyService.getVocabularyByTopicId(topicId);
        return new ResponseEntity<>(vocabularies, HttpStatus.OK);
    }

    /**
     * Count vocabulary by topic ID.
     * 
     * @param topicId The topic ID.
     * @return ResponseEntity containing the count of vocabulary entries.
     */
    @GetMapping("/count/{topicId}")
    public ResponseEntity<Map<String, Integer>> countVocabularyByTopicId(@PathVariable @Positive Integer topicId) {
        Integer count = vocabularyService.countVocabularyByTopicId(topicId);
        Map<String, Integer> response = new HashMap<>();
        response.put("count_vocabulary", count);
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing vocabulary.
     * 
     * @param vocabularyId    The ID of the vocabulary to update.
     * @param vocabularyDTOIn The DTO containing updated information.
     * @return ResponseEntity with a message indicating the result.
     */
    @PutMapping("/update/{vocabularyId}")
    public ResponseEntity<String> updateVocabulary(@PathVariable @Positive Integer vocabularyId,
            @Valid @RequestBody VocabularyDTOIn vocabularyDTOIn) {

        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }

        try {
            vocabularyService.updateVocabulary(vocabularyId, vocabularyDTOIn);
            return new ResponseEntity<>("Vocabulary updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to update Vocabulary: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Soft delete a vocabulary.
     * 
     * @param vocabularyId The ID of the vocabulary to delete.
     * @return ResponseEntity with a message indicating the result.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> softDeleteVocabulary(@RequestBody List<Integer> vocabularyListIds) {
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }
        StringBuilder responseMessage = new StringBuilder();
        boolean allSuccess = true;

        for (Integer vocabularyId : vocabularyListIds) {
            try {
                vocabularyService.softDeleteVocabulary(vocabularyId);
                responseMessage.append("Vocabulary ID ").append(vocabularyId)
                        .append(" deleted successfully. ");
            } catch (RuntimeException e) {
                responseMessage.append("Vocabulary ID ").append(vocabularyId).append(" not found. ");
                allSuccess = false;
            } catch (Exception e) {
                responseMessage.append("Error deleting Vocabulary ID ").append(vocabularyId).append(": ")
                        .append(e.getMessage())
                        .append(" ");
                allSuccess = false;
            }
        }

        return new ResponseEntity<>(responseMessage.toString(),
                allSuccess ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT);
    }
}
