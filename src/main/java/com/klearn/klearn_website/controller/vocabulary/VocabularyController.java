package com.klearn.klearn_website.controller.vocabulary;

import com.klearn.klearn_website.dto.dtoin.VocabularyDTOIn;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.service.vocabulary.VocabularyService;

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

    /**
     * Create a new vocabulary.
     * 
     * @param vocabularyDTOIn The DTO containing vocabulary details.
     * @return ResponseEntity with a message indicating the result.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createVocabulary(@RequestBody VocabularyDTOIn vocabularyDTOIn) {
        try {
            vocabularyService.createVocabulary(vocabularyDTOIn);
            return new ResponseEntity<>("Vocabulary created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create Vocabulary: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve all vocabulary by topic ID.
     * 
     * @param topicId The topic ID.
     * @return List of Vocabulary entities.
     */
    @GetMapping("/{topicId}")
    public ResponseEntity<List<Vocabulary>> getVocabularyByTopicId(@PathVariable Integer topicId) {
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
    public ResponseEntity<Map<String, Integer>> countVocabularyByTopicId(@PathVariable Integer topicId) {
        Integer count = vocabularyService.countVocabularyByTopicId(topicId);
        Map<String, Integer> response = new HashMap<>();
        response.put("count_vocabulary", count);
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing vocabulary.
     * 
     * @param vocabularyId The ID of the vocabulary to update.
     * @param vocabularyDTOIn The DTO containing updated information.
     * @return ResponseEntity with a message indicating the result.
     */
    @PutMapping("/update/{vocabularyId}")
    public ResponseEntity<String> updateVocabulary(@PathVariable Integer vocabularyId, @RequestBody VocabularyDTOIn vocabularyDTOIn) {
        try {
            vocabularyService.updateVocabulary(vocabularyId, vocabularyDTOIn);
            return new ResponseEntity<>("Vocabulary updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to update Vocabulary: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Soft delete a vocabulary.
     * 
     * @param vocabularyId The ID of the vocabulary to delete.
     * @return ResponseEntity with a message indicating the result.
     */
    @DeleteMapping("/soft-delete/{vocabularyId}")
    public ResponseEntity<String> softDeleteVocabulary(@PathVariable Integer vocabularyId) {
        try {
            vocabularyService.softDeleteVocabulary(vocabularyId);
            return new ResponseEntity<>("Vocabulary soft deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to delete Vocabulary: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Permanently delete a vocabulary.
     * 
     * @param vocabularyId The ID of the vocabulary to delete.
     * @return ResponseEntity with a message indicating the result.
     */
    @DeleteMapping("/delete/{vocabularyId}")
    public ResponseEntity<String> deleteVocabularyPermanently(@PathVariable Integer vocabularyId) {
        try {
            vocabularyService.deleteVocabularyPermanently(vocabularyId);
            return new ResponseEntity<>("Vocabulary permanently deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to delete Vocabulary: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
