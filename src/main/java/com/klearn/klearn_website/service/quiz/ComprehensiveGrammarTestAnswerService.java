package com.klearn.klearn_website.service.quiz;

import com.klearn.klearn_website.mapper.ComprehensiveGrammarTestAnswerMapper;
import com.klearn.klearn_website.model.ComprehensiveGrammarTestAnswer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComprehensiveGrammarTestAnswerService {

    private final ComprehensiveGrammarTestAnswerMapper comprehensiveGrammarTestAnswerMapper;

    // Insert a new ComprehensiveGrammarTestAnswer entry
    public void insertComprehensiveGrammarTestAnswer(ComprehensiveGrammarTestAnswer answer) {
        comprehensiveGrammarTestAnswerMapper.insertComprehensiveGrammarTestAnswer(answer);
    }

    // Update an existing ComprehensiveGrammarTestAnswer entry
    public void updateComprehensiveGrammarTestAnswer(ComprehensiveGrammarTestAnswer answer) {
        comprehensiveGrammarTestAnswerMapper.updateComprehensiveGrammarTestAnswer(answer);
    }

    // Soft delete a ComprehensiveGrammarTestAnswer entry
    public void softDeleteComprehensiveGrammarTestAnswer(Integer id) {
        comprehensiveGrammarTestAnswerMapper.softDeleteComprehensiveGrammarTestAnswer(id);
    }

    // Permanently delete a ComprehensiveGrammarTestAnswer entry
    public void deleteComprehensiveGrammarTestAnswerPermanently(Integer id) {
        comprehensiveGrammarTestAnswerMapper.deleteComprehensiveGrammarTestAnswerPermanently(id);
    }

    // Retrieve a ComprehensiveGrammarTestAnswer entry by ID
    public ComprehensiveGrammarTestAnswer getComprehensiveGrammarTestAnswerById(Integer id) {
        return comprehensiveGrammarTestAnswerMapper.getComprehensiveGrammarTestAnswerById(id);
    }

    // Retrieve all active ComprehensiveGrammarTestAnswer entries
    public List<ComprehensiveGrammarTestAnswer> getAllActiveComprehensiveGrammarTestAnswers() {
        return comprehensiveGrammarTestAnswerMapper.getAllActiveComprehensiveGrammarTestAnswers();
    }

    // Retrieve ComprehensiveGrammarTestAnswer by test ID with all related information
    public List<ComprehensiveGrammarTestAnswer> getComprehensiveGrammarTestAnswersByTestId(Integer testId) {
        return comprehensiveGrammarTestAnswerMapper.getComprehensiveGrammarTestAnswersByTestId(testId);
    }
}
