package com.klearn.klearn_website.service.grammar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klearn.klearn_website.dto.dtoin.QuestionGrammarDTOIn;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.model.QuestionGrammar;
import com.klearn.klearn_website.mapper.QuestionGrammarMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionGrammarService {

    private final QuestionGrammarMapper questionGrammarMapper;
    private final GrammarService grammarService;
    private final ObjectMapper objectMapper;

    /**
     * Create a new QuestionGrammar entry.
     *
     * @param questionGrammarDTOIn The DTO containing the details for the new QuestionGrammar.
     */
    public void createQuestionGrammar(QuestionGrammarDTOIn questionGrammarDTOIn) {
        String incorrectAnswersAsString = convertListToString(questionGrammarDTOIn.getIncorrect_answer());

        Grammar grammar = grammarService.getGrammarById(questionGrammarDTOIn.getGrammar_id())
                .orElseThrow(() -> new RuntimeException("Grammar not found with ID: " + questionGrammarDTOIn.getGrammar_id()));

        QuestionGrammar questionGrammar = new QuestionGrammar();
        questionGrammar.setQuestion_text(questionGrammarDTOIn.getQuestion_text());
        questionGrammar.setCorrect_answer(questionGrammarDTOIn.getCorrect_answer());
        questionGrammar.setIncorrect_answer(incorrectAnswersAsString);
        questionGrammar.setQuiz_type(questionGrammarDTOIn.getQuiz_type());
        questionGrammar.setGrammar(grammar);
        questionGrammar.setCreated_at(LocalDateTime.now());
        questionGrammar.setLast_modified(LocalDateTime.now());
        questionGrammar.setIs_deleted(false);

        questionGrammarMapper.insertQuestionGrammar(questionGrammar);
    }

    /**
     * Update an existing QuestionGrammar entry.
     *
     * @param id                   The ID of the QuestionGrammar to update.
     * @param questionGrammarDTOIn The DTO containing the updated details.
     */
    public void updateQuestionGrammar(Integer id, QuestionGrammarDTOIn questionGrammarDTOIn) {
        String incorrectAnswersAsString = convertListToString(questionGrammarDTOIn.getIncorrect_answer());

        Grammar grammar = grammarService.getGrammarById(questionGrammarDTOIn.getGrammar_id())
                .orElseThrow(() -> new RuntimeException("Grammar not found with ID: " + questionGrammarDTOIn.getGrammar_id()));

        QuestionGrammar existingQuestion = questionGrammarMapper.getQuestionById(id);
        if (existingQuestion == null || existingQuestion.getIs_deleted()) {
            throw new RuntimeException("QuestionGrammar not found with ID: " + id);
        }

        existingQuestion.setQuestion_text(questionGrammarDTOIn.getQuestion_text());
        existingQuestion.setCorrect_answer(questionGrammarDTOIn.getCorrect_answer());
        existingQuestion.setIncorrect_answer(incorrectAnswersAsString);
        existingQuestion.setQuiz_type(questionGrammarDTOIn.getQuiz_type());
        existingQuestion.setGrammar(grammar);
        existingQuestion.setLast_modified(LocalDateTime.now());

        questionGrammarMapper.updateQuestionGrammar(existingQuestion);
    }

    /**
     * Soft delete a QuestionGrammar entry.
     *
     * @param id The ID of the QuestionGrammar to soft delete.
     */
    public void softDeleteQuestionGrammar(Integer id) {
        QuestionGrammar existingQuestion = questionGrammarMapper.getQuestionById(id);
        if (existingQuestion == null || existingQuestion.getIs_deleted()) {
            throw new RuntimeException("QuestionGrammar not found with ID: " + id);
        }

        questionGrammarMapper.softDeleteQuestionGrammar(id);
    }

    /**
     * Permanently delete a QuestionGrammar entry.
     *
     * @param id The ID of the QuestionGrammar to delete.
     */
    public void deleteQuestionGrammarPermanently(Integer id) {
        QuestionGrammar existingQuestion = questionGrammarMapper.getQuestionById(id);
        if (existingQuestion == null) {
            throw new RuntimeException("QuestionGrammar not found with ID: " + id);
        }

        questionGrammarMapper.deleteQuestionGrammarPermanently(id);
    }

    /**
     * Get all QuestionGrammar entries by Grammar ID.
     *
     * @param grammarId The ID of the grammar.
     * @return A list of QuestionGrammar entries.
     */
    public List<QuestionGrammar> getAllQuestionsByGrammarId(Integer grammarId) {
        return questionGrammarMapper.getQuestionsByGrammarId(grammarId);
    }

    /**
     * Get a QuestionGrammar entry by its ID.
     *
     * @param id The ID of the QuestionGrammar.
     * @return The QuestionGrammar entry.
     */
    public QuestionGrammar getQuestionById(Integer id) {
        QuestionGrammar question = questionGrammarMapper.getQuestionById(id);
        if (question == null || question.getIs_deleted()) {
            throw new RuntimeException("QuestionGrammar not found with ID: " + id);
        }
        return question;
    }

    /**
     * Check if a QuestionGrammar entry exists by Grammar ID.
     *
     * @param grammarId The ID of the grammar.
     * @return True if the QuestionGrammar exists, false otherwise.
     */
    public boolean existsByGrammarId(Integer grammarId) {
        return questionGrammarMapper.existsByGrammarId(grammarId);
    }

    /**
     * Get all active QuestionGrammar entries.
     *
     * @return A list of active QuestionGrammar entries.
     */
    public List<QuestionGrammar> getAllActiveQuestions() {
        return questionGrammarMapper.getAllActiveQuestions();
    }

    private String convertListToString(List<String> incorrectAnswers) {
        try {
            return objectMapper.writeValueAsString(incorrectAnswers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting incorrect answers to string", e);
        }
    }
}
