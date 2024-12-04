package com.klearn.klearn_website.controller.grammar;

import com.klearn.klearn_website.dto.dtoin.QuestionGrammarDTOIn;
import com.klearn.klearn_website.model.QuestionGrammar;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.grammar.QuestionGrammarService;
import com.klearn.klearn_website.service.user.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question_grammar")
@AllArgsConstructor
public class QuestionGrammarController {

    private final QuestionGrammarService questionGrammarService;
    private final UserService userService;

    /**
     * Create a new QuestionGrammar entry.
     *
     * @param questionGrammarDTOIn The DTO containing the details for the new
     *                             QuestionGrammar.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createQuestionGrammar(
            @Valid @RequestBody QuestionGrammarDTOIn questionGrammarDTOIn) {

        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }

        questionGrammarService.createQuestionGrammar(questionGrammarDTOIn);
        return ResponseEntity.ok("Question grammar created successfully");
    }

    /**
     * Update an existing QuestionGrammar entry.
     *
     * @param id                   The ID of the QuestionGrammar to update.
     * @param questionGrammarDTOIn The DTO containing the updated details.
     * @return ResponseEntity with a success message.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuestionGrammar(
            @PathVariable @Positive Integer id,
            @Valid @RequestBody QuestionGrammarDTOIn questionGrammarDTOIn) {
                User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }
        
        questionGrammarService.updateQuestionGrammar(id, questionGrammarDTOIn);
        return ResponseEntity.ok("Question grammar updated successfully");
    }

    /**
     * Soft delete a QuestionGrammar entry.
     *
     * @param id The ID of the QuestionGrammar to soft delete.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> softDeleteQuestionGrammar(@RequestBody List<Integer> listQuestionIds) {     
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }

        StringBuilder responseMessage = new StringBuilder();
        boolean allSuccess = true;

        for (Integer questionId : listQuestionIds) {
            try {
                questionGrammarService.softDeleteQuestionGrammar(questionId);
                responseMessage.append("Question ID ").append(questionId)
                        .append(" deleted successfully. ");
            } catch (RuntimeException e) {
                responseMessage.append("Question ID ").append(questionId).append(" not found. ");
                allSuccess = false;
            } catch (Exception e) {
                responseMessage.append("Error deleting Question ID ").append(questionId).append(": ")
                        .append(e.getMessage())
                        .append(" ");
                allSuccess = false;
            }
        }

        return new ResponseEntity<>(responseMessage.toString(),
                allSuccess ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT);
    }

    /**
     * Get all QuestionGrammar entries by Grammar ID.
     *
     * @param grammarId The ID of the grammar.
     * @return ResponseEntity containing a list of QuestionGrammar entries.
     */
    @GetMapping("/grammar/{grammarId}")
    public ResponseEntity<List<QuestionGrammar>> getAllQuestionsByGrammarId(@PathVariable @Positive Integer grammarId) {
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
    public ResponseEntity<QuestionGrammar> getQuestionById(@PathVariable @Positive Integer id) {
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
    public ResponseEntity<Boolean> existsByGrammarId(@PathVariable @Positive Integer grammarId) {
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
