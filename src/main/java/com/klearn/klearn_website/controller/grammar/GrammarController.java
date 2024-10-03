package com.klearn.klearn_website.controller.grammar;

import com.klearn.klearn_website.dto.grammar.CreateGrammarDto;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.service.grammar.GrammarService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
@RequestMapping("/api/grammar")
public class GrammarController {
  
  private GrammarService grammarService;

  public GrammarController(GrammarService grammarService) {
    this.grammarService = grammarService;
  }

  @GetMapping
  public List<Grammar> getAllGrammar() {
    return grammarService.getAllGrammar();
  }

  @GetMapping("/{courseId}")
  public List<Grammar> getGrammarByCourseId(@PathVariable Integer courseId) {
    return grammarService.getGrammarByCourseId(courseId);
  }
  
  @PostMapping("/create")
  public ResponseEntity<String> createGrammar(@RequestBody CreateGrammarDto createGrammarDto) {
    try {
      grammarService.createGrammar(createGrammarDto);
      return new ResponseEntity<>("Grammar Lesson created successfully", HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>("Failed to create Grammar Lesson: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
