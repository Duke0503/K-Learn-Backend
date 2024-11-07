package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.ComprehensiveGrammarTestAnswer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComprehensiveGrammarTestAnswerMapper {

    // Insert a new ComprehensiveGrammarTestAnswer entry
    @Insert("INSERT INTO comprehensive_grammar_test_answer (user_answer, options, last_modified, is_deleted, is_correct, grammar_id, comprehensive_test_results_id, question_grammar_id) " +
            "VALUES (#{user_answer}, #{options}, #{last_modified}, #{is_deleted}, #{is_correct}, #{grammar.id}, #{comprehensiveTestResults.id}, #{questionGrammar.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertComprehensiveGrammarTestAnswer(ComprehensiveGrammarTestAnswer answer);

    // Update an existing ComprehensiveGrammarTestAnswer entry
    @Update("UPDATE comprehensive_grammar_test_answer SET user_answer = #{user_answer}, options = #{options}, last_modified = #{last_modified}, is_deleted = #{is_deleted}, is_correct = #{is_correct}, grammar_id = #{grammar.id}, comprehensive_test_results_id = #{comprehensiveTestResults.id}, question_grammar_id = #{questionGrammar.id} " +
            "WHERE id = #{id}")
    void updateComprehensiveGrammarTestAnswer(ComprehensiveGrammarTestAnswer answer);

    // Soft delete a ComprehensiveGrammarTestAnswer entry
    @Update("UPDATE comprehensive_grammar_test_answer SET is_deleted = true, last_modified = NOW() WHERE id = #{id}")
    void softDeleteComprehensiveGrammarTestAnswer(Integer id);

    // Permanently delete a ComprehensiveGrammarTestAnswer entry
    @Delete("DELETE FROM comprehensive_grammar_test_answer WHERE id = #{id}")
    void deleteComprehensiveGrammarTestAnswerPermanently(Integer id);

    // Retrieve a ComprehensiveGrammarTestAnswer entry by ID
    @Select("SELECT * FROM comprehensive_grammar_test_answer WHERE id = #{id} AND is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user_answer", column = "user_answer"),
            @Result(property = "options", column = "options"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "is_correct", column = "is_correct"),
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "comprehensiveTestResults.id", column = "comprehensive_test_results_id"),
            @Result(property = "questionGrammar.id", column = "question_grammar_id")
    })
    ComprehensiveGrammarTestAnswer getComprehensiveGrammarTestAnswerById(Integer id);

    // Retrieve all active ComprehensiveGrammarTestAnswer entries
    @Select("SELECT * FROM comprehensive_grammar_test_answer WHERE is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user_answer", column = "user_answer"),
            @Result(property = "options", column = "options"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "is_correct", column = "is_correct"),
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "comprehensiveTestResults.id", column = "comprehensive_test_results_id"),
            @Result(property = "questionGrammar.id", column = "question_grammar_id")
    })
    List<ComprehensiveGrammarTestAnswer> getAllActiveComprehensiveGrammarTestAnswers();

    // Retrieve ComprehensiveGrammarTestAnswer by test ID with all related information
    @Select("SELECT cga.*, g.*, ctr.*, qg.* FROM comprehensive_grammar_test_answer cga " +
            "JOIN grammar g ON cga.grammar_id = g.id " +
            "JOIN comprehensive_test_results ctr ON cga.comprehensive_test_results_id = ctr.id " +
            "JOIN question_grammar qg ON cga.question_grammar_id = qg.id " +
            "WHERE cga.comprehensive_test_results_id = #{testId} AND cga.is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user_answer", column = "user_answer"),
            @Result(property = "options", column = "options"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "is_correct", column = "is_correct"),
            // Map the Grammar object
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "grammar.grammar_name", column = "grammar_name"),
            @Result(property = "grammar.grammar_description", column = "grammar_description"),
            @Result(property = "grammar.explanation", column = "explanation"),
            @Result(property = "grammar.example", column = "example"),
            @Result(property = "grammar.lesson_number", column = "lesson_number"),
            @Result(property = "grammar.last_modified", column = "g.last_modified"),
            @Result(property = "grammar.is_deleted", column = "g.is_deleted"),
            // Map the ComprehensiveTestResults object
            @Result(property = "comprehensiveTestResults.id", column = "comprehensive_test_results_id"),
            @Result(property = "comprehensiveTestResults.test_type", column = "test_type"),
            @Result(property = "comprehensiveTestResults.test_date", column = "test_date"),
            @Result(property = "comprehensiveTestResults.no_correct_questions", column = "no_correct_questions"),
            @Result(property = "comprehensiveTestResults.no_incorrect_questions", column = "no_incorrect_questions"),
            @Result(property = "comprehensiveTestResults.last_modified", column = "ctr.last_modified"),
            @Result(property = "comprehensiveTestResults.is_deleted", column = "ctr.is_deleted"),
            // Map the QuestionGrammar object
            @Result(property = "questionGrammar.id", column = "question_grammar_id"),
            @Result(property = "questionGrammar.question_text", column = "question_text"),
            @Result(property = "questionGrammar.correct_answer", column = "correct_answer"),
            @Result(property = "questionGrammar.incorrect_answer", column = "incorrect_answer"),
            @Result(property = "questionGrammar.quiz_type", column = "quiz_type"),
            @Result(property = "questionGrammar.last_modified", column = "qg.last_modified"),
            @Result(property = "questionGrammar.is_deleted", column = "qg.is_deleted")
    })
    List<ComprehensiveGrammarTestAnswer> getComprehensiveGrammarTestAnswersByTestId(@Param("testId") Integer testId);
}
