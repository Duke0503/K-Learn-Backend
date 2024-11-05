package com.klearn.klearn_website.controller.vocabulary;

import com.klearn.klearn_website.dto.dtoin.VocabularyTopicDTOIn;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.service.vocabulary.VocabularyTopicService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/vocabulary_topic")
public class VocabularyTopicController {

    private final VocabularyTopicService vocabularyTopicService;

    /**
     * Retrieves all vocabulary topics that are not deleted.
     *
     * @return ResponseEntity containing a list of all vocabulary topics.
     */
    @GetMapping
    public ResponseEntity<List<VocabularyTopic>> getAllVocabularyTopics() {
        List<VocabularyTopic> vocabularyTopics = vocabularyTopicService.getAllVocabularyTopics();
        return new ResponseEntity<>(vocabularyTopics, HttpStatus.OK);
    }

    /**
     * Retrieves all vocabulary topics associated with a specific course ID.
     *
     * @param courseId The ID of the course.
     * @return ResponseEntity containing a list of vocabulary topics for the specified course.
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<VocabularyTopic>> getVocabularyTopicsByCourseId(@PathVariable @Positive Integer courseId) {
        List<VocabularyTopic> vocabularyTopics = vocabularyTopicService.getVocabularyTopicsByCourseId(courseId);
        return new ResponseEntity<>(vocabularyTopics, HttpStatus.OK);
    }

    /**
     * Retrieves a specific vocabulary topic by its ID.
     *
     * @param topicId The ID of the vocabulary topic to retrieve.
     * @return ResponseEntity containing the vocabulary topic, if found.
     */
    @GetMapping("/{topicId}")
    public ResponseEntity<VocabularyTopic> getVocabularyTopicById(@PathVariable @Positive Integer topicId) {
        Optional<VocabularyTopic> vocabularyTopic = vocabularyTopicService.getVocabularyTopicById(topicId);
        return vocabularyTopic.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new vocabulary topic.
     *
     * @param vocabularyTopicDTOIn The DTO containing vocabulary topic details.
     * @return ResponseEntity indicating the result of the create operation.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createVocabularyTopic(@Valid @RequestBody VocabularyTopicDTOIn vocabularyTopicDTOIn) {
        try {
            vocabularyTopicService.createVocabularyTopic(vocabularyTopicDTOIn);
            return new ResponseEntity<>("Vocabulary Topic created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to create Vocabulary Topic: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing vocabulary topic by its ID.
     *
     * @param topicId              The ID of the vocabulary topic to update.
     * @param vocabularyTopicDTOIn The DTO containing updated information.
     * @return ResponseEntity indicating the result of the update operation.
     */
    @PutMapping("/update/{topicId}")
    public ResponseEntity<String> updateVocabularyTopic(@PathVariable @Positive Integer topicId,
                                                        @Valid @RequestBody VocabularyTopicDTOIn vocabularyTopicDTOIn) {
        try {
            vocabularyTopicService.updateVocabularyTopic(topicId, vocabularyTopicDTOIn);
            return new ResponseEntity<>("Vocabulary Topic updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to update Vocabulary Topic: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Soft deletes a vocabulary topic by its ID.
     *
     * @param topicId The ID of the vocabulary topic to delete.
     * @return ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("/delete/{topicId}")
    public ResponseEntity<String> deleteVocabularyTopic(@PathVariable @Positive Integer topicId) {
        try {
            vocabularyTopicService.softDeleteVocabularyTopic(topicId);
            return new ResponseEntity<>("Vocabulary Topic deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to delete Vocabulary Topic: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
