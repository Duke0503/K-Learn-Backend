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

    /**
     * Create a new QuestionGrammar entry.
     *
     * @param questionGrammarDTOIn The DTO containing the details for the new QuestionGrammar.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createQuestionGrammar(
            @RequestBody QuestionGrammarDTOIn questionGrammarDTOIn) {
        questionGrammarService.createQuestionGrammar(questionGrammarDTOIn);
        return ResponseEntity.ok("Question grammar created successfully");
    }

    /**
     * Update an existing QuestionGrammar entry.
     *
     * @param id The ID of the QuestionGrammar to update.
     * @param questionGrammarDTOIn The DTO containing the updated details.
     * @return ResponseEntity with a success message.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuestionGrammar(
            @PathVariable Integer id,
            @RequestBody QuestionGrammarDTOIn questionGrammarDTOIn) {
        questionGrammarService.updateQuestionGrammar(id, questionGrammarDTOIn);
        return ResponseEntity.ok("Question grammar updated successfully");
    }

    /**
     * Soft delete a QuestionGrammar entry.
     *
     * @param id The ID of the QuestionGrammar to soft delete.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/soft_delete/{id}")
    public ResponseEntity<String> softDeleteQuestionGrammar(@PathVariable Integer id) {
        questionGrammarService.softDeleteQuestionGrammar(id);
        return ResponseEntity.ok("Question grammar soft deleted successfully");
    }

    /**
     * Permanently delete a QuestionGrammar entry.
     *
     * @param id The ID of the QuestionGrammar to delete.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestionGrammarPermanently(@PathVariable Integer id) {
        questionGrammarService.deleteQuestionGrammarPermanently(id);
        return ResponseEntity.ok("Question grammar permanently deleted successfully");
    }

    /**
     * Get all QuestionGrammar entries by Grammar ID.
     *
     * @param grammarId The ID of the grammar.
     * @return ResponseEntity containing a list of QuestionGrammar entries.
     */
    @GetMapping("/grammar/{grammarId}")
    public ResponseEntity<List<QuestionGrammar>> getAllQuestionsByGrammarId(@PathVariable Integer grammarId) {
        List<QuestionGrammar> questions = questionGrammarService.getAllQuestionsByGrammarId(grammarId);
        if (questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questions);
    }

    /**
     * Get a QuestionGrammar entry by its ID.
     *
     * @param id The ID of the QuestionGrammar.
     * @return ResponseEntity containing the QuestionGrammar entry.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionGrammar> getQuestionById(@PathVariable Integer id) {
        QuestionGrammar question = questionGrammarService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    /**
     * Check if a QuestionGrammar entry exists by Grammar ID.
     *
     * @param grammarId The ID of the grammar.
     * @return ResponseEntity containing a boolean indicating existence.
     */
    @GetMapping("/exists/{grammarId}")
    public ResponseEntity<Boolean> existsByGrammarId(@PathVariable Integer grammarId) {
        boolean exists = questionGrammarService.existsByGrammarId(grammarId);
        return ResponseEntity.ok(exists);
    }

    /**
     * Get all active QuestionGrammar entries.
     *
     * @return ResponseEntity containing a list of active QuestionGrammar entries.
     */
    @GetMapping("/all_active")
    public ResponseEntity<List<QuestionGrammar>> getAllActiveQuestions() {
        List<QuestionGrammar> questions = questionGrammarService.getAllActiveQuestions();
        if (questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questions);
    }
}
