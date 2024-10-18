package com.klearn.klearn_website.controller.grammar;

import com.klearn.klearn_website.dto.dtoin.GrammarDTOIn;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.service.grammar.GrammarService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/grammar")
public class GrammarController {

    private GrammarService grammarService;

    /**
     * Retrieve all Grammar entries that are not deleted.
     *
     * @return A list of all available Grammar entries.
     */
    @GetMapping
    public List<Grammar> getAllGrammar() {
        return grammarService.getAllGrammar();
    }

    /**
     * Retrieve all Grammar entries by Course ID.
     *
     * @param courseId The ID of the course to retrieve grammar entries for.
     * @return A list of Grammar entries related to the specified course.
     */
    @GetMapping("/{courseId}")
    public List<Grammar> getGrammarByCourseId(@PathVariable Integer courseId) {
        return grammarService.getGrammarByCourseId(courseId);
    }

    /**
     * Create a new Grammar entry.
     *
     * @param grammarDTOIn The DTO containing grammar details to create.
     * @return ResponseEntity indicating the result of the create operation.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createGrammar(@RequestBody GrammarDTOIn grammarDTOIn) {
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
     * @return ResponseEntity containing the Grammar entry if found, or a not found status.
     */
    @GetMapping("/entry/{grammarId}")
    public ResponseEntity<Grammar> getGrammarById(@PathVariable Integer grammarId) {
        return grammarService.getGrammarById(grammarId)
                .map(grammar -> new ResponseEntity<>(grammar, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update an existing Grammar entry.
     *
     * @param grammarId   The ID of the grammar entry to update.
     * @param grammarDTOIn The DTO containing the updated grammar details.
     * @return ResponseEntity indicating the result of the update operation.
     */
    @PutMapping("/update/{grammarId}")
    public ResponseEntity<String> updateGrammar(@PathVariable Integer grammarId, @RequestBody GrammarDTOIn grammarDTOIn) {
        try {
            grammarService.updateGrammar(grammarId, grammarDTOIn);
            return new ResponseEntity<>("Grammar updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to update Grammar: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Soft delete a Grammar entry by marking it as deleted.
     *
     * @param grammarId The ID of the grammar entry to delete.
     * @return ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("/soft-delete/{grammarId}")
    public ResponseEntity<String> softDeleteGrammar(@PathVariable Integer grammarId) {
        try {
            grammarService.softDeleteGrammar(grammarId);
            return new ResponseEntity<>("Grammar soft deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to delete Grammar: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Permanently delete a Grammar entry.
     *
     * @param grammarId The ID of the grammar entry to delete permanently.
     * @return ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("/delete/{grammarId}")
    public ResponseEntity<String> deleteGrammarPermanently(@PathVariable Integer grammarId) {
        try {
            grammarService.deleteGrammarPermanently(grammarId);
            return new ResponseEntity<>("Grammar permanently deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to delete Grammar: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
