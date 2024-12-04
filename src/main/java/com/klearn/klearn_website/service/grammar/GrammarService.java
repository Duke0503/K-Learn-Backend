package com.klearn.klearn_website.service.grammar;

import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.dto.dtoin.GrammarDTOIn;
import com.klearn.klearn_website.mapper.GrammarMapper;
import com.klearn.klearn_website.service.course.CourseService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GrammarService {
    private final CourseService courseService;
    private final GrammarMapper grammarMapper;

    /**
     * Create a new Grammar entry.
     *
     * @param grammarDTOIn The DTO containing grammar details to create.
     */
    public void createGrammar(GrammarDTOIn grammarDTOIn) {
        Grammar grammar = new Grammar();

        grammar.setGrammar_name(grammarDTOIn.getGrammar_name());
        grammar.setGrammar_description(grammarDTOIn.getGrammar_description());
        grammar.setExplanation(grammarDTOIn.getExplanation());
        grammar.setExample(grammarDTOIn.getExample());
        grammar.setLesson_number(grammarDTOIn.getLesson_number());
        grammar.setIs_deleted(false);
        grammar.setLast_modified(LocalDateTime.now());

        Course course = courseService.getCourseById(grammarDTOIn.getCourse_id()).orElseThrow(
                () -> new RuntimeException("Course not found with ID: " + grammarDTOIn.getCourse_id()));

        grammar.setCourse(course);

        grammarMapper.insertGrammar(grammar);
    }

    /**
     * Retrieve all Grammar entries that are not deleted.
     *
     * @return A list of all available Grammar entries.
     */
    public List<Grammar> getAllGrammar() {
        return grammarMapper.getAllGrammar();
    }

    /**
     * Retrieve all Grammar entries by Course ID.
     *
     * @param courseId The ID of the course to retrieve grammar entries for.
     * @return A list of Grammar entries related to the specified course.
     */
    public List<Grammar> getGrammarByCourseId(Integer courseId) {
        return grammarMapper.getAllByCourseId(courseId);
    }

    /**
     * Retrieve a Grammar entry by ID.
     *
     * @param grammarId The ID of the grammar entry to retrieve.
     * @return An Optional containing the Grammar entry if found, or empty if not.
     */
    public Optional<Grammar> getGrammarById(Integer grammarId) {
        return Optional.ofNullable(grammarMapper.getGrammarById(grammarId));
    }

    /**
     * Update an existing Grammar entry.
     *
     * @param grammarId   The ID of the grammar entry to update.
     * @param grammarDTOIn The DTO containing the updated grammar details.
     */
    public void updateGrammar(Integer grammarId, GrammarDTOIn grammarDTOIn) {
        Grammar existingGrammar = grammarMapper.getGrammarById(grammarId);
        if (existingGrammar == null) {
            throw new RuntimeException("Grammar not found with ID: " + grammarId);
        }

        existingGrammar.setGrammar_name(grammarDTOIn.getGrammar_name());
        existingGrammar.setGrammar_description(grammarDTOIn.getGrammar_description());
        existingGrammar.setExplanation(grammarDTOIn.getExplanation());
        existingGrammar.setExample(grammarDTOIn.getExample());
        existingGrammar.setLesson_number(grammarDTOIn.getLesson_number());
        existingGrammar.setLast_modified(LocalDateTime.now());

        grammarMapper.updateGrammar(existingGrammar);
    }

    /**
     * Soft delete a Grammar entry.
     *
     * @param grammarId The ID of the grammar entry to delete.
     */
    public void softDeleteGrammar(Integer grammarId) {
        Grammar existingGrammar = grammarMapper.getGrammarById(grammarId);
        if (existingGrammar == null) {
            throw new RuntimeException("Grammar not found with ID: " + grammarId);
        }

        grammarMapper.softDeleteGrammar(grammarId);
    }

    /**
     * Permanently delete a Grammar entry.
     *
     * @param grammarId The ID of the grammar entry to delete permanently.
     */
    public void deleteGrammarPermanently(Integer grammarId) {
        Grammar existingGrammar = grammarMapper.getGrammarById(grammarId);
        if (existingGrammar == null) {
            throw new RuntimeException("Grammar not found with ID: " + grammarId);
        }

        grammarMapper.deleteGrammarPermanently(grammarId);
    }
}
