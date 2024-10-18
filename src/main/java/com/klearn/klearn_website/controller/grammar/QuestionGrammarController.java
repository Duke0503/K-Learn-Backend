package com.klearn.klearn_website.controller.grammar;

import com.klearn.klearn_website.dto.dtoin.QuestionGrammarDTOIn;
import com.klearn.klearn_website.model.QuestionGrammar;
import com.klearn.klearn_website.service.grammar.QuestionGrammarService;
import lombok.AllArgsConstructor;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question_grammar")
@AllArgsConstructor
public class QuestionGrammarController {

    private final QuestionGrammarService questionGrammarService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuestionGrammar(
            @RequestBody QuestionGrammarDTOIn questionGrammarDTOIn) {

        questionGrammarService.createQuestionGrammar(questionGrammarDTOIn);
        return ResponseEntity.ok("Question grammar created successfully");
    }

    @GetMapping("/grammar/{grammarId}")
    public ResponseEntity<List<QuestionGrammar>> getAllQuestionsByGrammarId(@PathVariable Integer grammarId) {
        List<QuestionGrammar> questions = questionGrammarService.getAllQuestionsByGrammarId(grammarId);
        if (questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questions);
    }
}
