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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {
  
  private VocabularyService vocabularyService;

  @PostMapping("/create")
  public ResponseEntity<String> createVocabulary(@RequestBody VocabularyDTOIn vocabularyDTOIn) {
    try {
      vocabularyService.createVocabulary(vocabularyDTOIn);
      return new ResponseEntity<>("Vocabulary created successfully", HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>("Failed to create Vocabulary: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{topic_id}")
  public List<Vocabulary> getVocabularyByTopicId(@PathVariable Integer topic_id) {
    return vocabularyService.getVocabularyByTopicId(topic_id);
  }

  @GetMapping("/count/{topic_id}")
  public ResponseEntity<Map<String, Integer>> countVocabularyByTopicId(@PathVariable Integer topic_id) {
    Integer count = vocabularyService.countVocabularyByTopicId(topic_id);

    Map<String, Integer> response = new HashMap<>();

    response.put("count_vocabulary", count);

    return ResponseEntity.ok(response);
  }

}
