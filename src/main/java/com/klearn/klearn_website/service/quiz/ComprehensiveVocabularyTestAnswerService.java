package com.klearn.klearn_website.service.quiz;

import com.klearn.klearn_website.mapper.ComprehensiveVocabularyTestAnswerMapper;
import com.klearn.klearn_website.model.ComprehensiveVocabularyTestAnswer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComprehensiveVocabularyTestAnswerService {

    private final ComprehensiveVocabularyTestAnswerMapper comprehensiveVocabularyTestAnswerMapper;

    // Insert a new ComprehensiveVocabularyTestAnswer entry
    public void insertComprehensiveVocabularyTestAnswer(ComprehensiveVocabularyTestAnswer answer) {
        comprehensiveVocabularyTestAnswerMapper.insertComprehensiveVocabularyTestAnswer(answer);
    }

    // Update an existing ComprehensiveVocabularyTestAnswer entry
    public void updateComprehensiveVocabularyTestAnswer(ComprehensiveVocabularyTestAnswer answer) {
        comprehensiveVocabularyTestAnswerMapper.updateComprehensiveVocabularyTestAnswer(answer);
    }

    // Soft delete a ComprehensiveVocabularyTestAnswer entry
    public void softDeleteComprehensiveVocabularyTestAnswer(Integer id) {
        comprehensiveVocabularyTestAnswerMapper.softDeleteComprehensiveVocabularyTestAnswer(id);
    }

    // Permanently delete a ComprehensiveVocabularyTestAnswer entry
    public void deleteComprehensiveVocabularyTestAnswerPermanently(Integer id) {
        comprehensiveVocabularyTestAnswerMapper.deleteComprehensiveVocabularyTestAnswerPermanently(id);
    }

    // Retrieve a ComprehensiveVocabularyTestAnswer entry by ID
    public ComprehensiveVocabularyTestAnswer getComprehensiveVocabularyTestAnswerById(Integer id) {
        return comprehensiveVocabularyTestAnswerMapper.getComprehensiveVocabularyTestAnswerById(id);
    }

    // Retrieve all active ComprehensiveVocabularyTestAnswer entries
    public List<ComprehensiveVocabularyTestAnswer> getAllActiveComprehensiveVocabularyTestAnswers() {
        return comprehensiveVocabularyTestAnswerMapper.getAllActiveComprehensiveVocabularyTestAnswers();
    }

    // Retrieve ComprehensiveVocabularyTestAnswer entries by test ID with all related information
    public List<ComprehensiveVocabularyTestAnswer> getComprehensiveVocabularyTestAnswersByTestId(Integer testId) {
        return comprehensiveVocabularyTestAnswerMapper.getComprehensiveVocabularyTestAnswersByTestId(testId);
    }
}
