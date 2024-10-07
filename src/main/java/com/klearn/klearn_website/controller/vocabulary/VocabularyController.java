package com.klearn.klearn_website.controller.vocabulary;

import com.klearn.klearn_website.dto.vocabulary.CreateVocabularyDto;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.service.vocabulary.VocabularyService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {
  
  private VocabularyService vocabularyService;

  private VocabularyController(VocabularyService vocabularyService) {
    this.vocabularyService = vocabularyService;
  }

  @PostMapping("/create")
  public ResponseEntity<String> createVocabulary(@RequestBody CreateVocabularyDto createVocabularyDto) {
    try {
      vocabularyService.createVocabulary(createVocabularyDto);
      return new ResponseEntity<>("Vocabulary created successfully", HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>("Failed to create Vocabulary: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{topic_id}")
  public List<Vocabulary> getVocabularyByTopicId(@PathVariable Integer topic_id) {
    return vocabularyService.getVocabularyByTopicId(topic_id);
  }
}
