package com.klearn.klearn_website.controller.grammar;

import com.klearn.klearn_website.dto.dtoin.GrammarDTOIn;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.grammar.GrammarService;
import com.klearn.klearn_website.service.user.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/grammar")
public class GrammarController {

    private final GrammarService grammarService;
    private final UserService userService;

    /**
     * Retrieve all Grammar entries by Course ID.
     *
     * @param courseId The ID of the course to retrieve grammar entries for.
     * @return A list of Grammar entries related to the specified course.
     */
    @GetMapping("/{courseId}")
    public List<Grammar> getGrammarByCourseId(@PathVariable @Positive Integer courseId) {
        return grammarService.getGrammarByCourseId(courseId);
    }

    /**
     * Create a new Grammar entry.
     *
     * @param grammarDTOIn The DTO containing grammar details to create.
     * @return ResponseEntity indicating the result of the create operation.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createGrammar(@Valid @RequestBody GrammarDTOIn grammarDTOIn) {
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }

        try {
            grammarService.createGrammar(grammarDTOIn);
            return new ResponseEntity<>("Grammar Lesson created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create Grammar Lesson: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve a specific Grammar entry by its ID.
     *
     * @param grammarId The ID of the grammar entry to retrieve.
     * @return ResponseEntity containing the Grammar entry if found, or a not found
     *         status.
     */
    @GetMapping("/entry/{grammarId}")
    public ResponseEntity<Grammar> getGrammarById(@PathVariable @Positive Integer grammarId) {
        return grammarService.getGrammarById(grammarId)
                .map(grammar -> new ResponseEntity<>(grammar, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update an existing Grammar entry.
     *
     * @param grammarId    The ID of the grammar entry to update.
     * @param grammarDTOIn The DTO containing the updated grammar details.
     * @return ResponseEntity indicating the result of the update operation.
     */
    @PutMapping("/update/{grammarId}")
    public ResponseEntity<String> updateGrammar(@PathVariable @Positive Integer grammarId,
            @Valid @RequestBody GrammarDTOIn grammarDTOIn) {
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }

        try {
            grammarService.updateGrammar(grammarId, grammarDTOIn);
            return new ResponseEntity<>("Grammar updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to update Grammar: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Soft delete a Grammar entry by marking it as deleted.
     *
     * @param grammarId The ID of the grammar entry to delete.
     * @return ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> softDeleteGrammar(@RequestBody List<Integer> grammarListIds) {
        User user = userService.getAuthenticatedUser();
        // Check if the user has the 'content-management' role (role number 2)
        if (user.getRole() != 2) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to update courses.",
                    HttpStatus.FORBIDDEN);
        }
        StringBuilder responseMessage = new StringBuilder();
        boolean allSuccess = true;

        for (Integer grammarId : grammarListIds) {
            try {
                grammarService.softDeleteGrammar(grammarId);
                responseMessage.append("Grammar ID ").append(grammarId)
                        .append(" deleted successfully. ");
            } catch (RuntimeException e) {
                responseMessage.append("Grammar ID ").append(grammarId).append(" not found. ");
                allSuccess = false;
            } catch (Exception e) {
                responseMessage.append("Error deleting Grammar ID ").append(grammarId).append(": ")
                        .append(e.getMessage())
                        .append(" ");
                allSuccess = false;
            }
        }
        return new ResponseEntity<>(responseMessage.toString(),
                allSuccess ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT);
    }
}
