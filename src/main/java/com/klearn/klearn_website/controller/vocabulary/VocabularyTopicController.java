package com.klearn.klearn_website.controller.vocabulary;

import com.klearn.klearn_website.dto.dtoin.VocabularyTopicDTOIn;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.service.vocabulary.VocabularyTopicService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/vocabulary_topic")
public class VocabularyTopicController {

    private VocabularyTopicService vocabularyTopicService;

    @GetMapping
    public List<VocabularyTopic> getAllVocabularyTopic() {
        return vocabularyTopicService.getAllVocabularyTopic();
    }

    @GetMapping("/{course_id}")
    public List<VocabularyTopic> getVocabularyTopicsById(@PathVariable Integer course_id) {
        return vocabularyTopicService.getVocabularyTopicById(course_id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVocabularyTopic(@RequestBody VocabularyTopicDTOIn vocabularyTopicDTOIn) {
        try {
            vocabularyTopicService.createVocabularyTopic(vocabularyTopicDTOIn);
            return new ResponseEntity<>("Vocabulary Topic created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create Vocabulary Topic: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
