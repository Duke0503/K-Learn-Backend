package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.ComprehensiveVocabularyTestAnswer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComprehensiveVocabularyTestAnswerMapper {

    // Insert a new ComprehensiveVocabularyTestAnswer entry
    @Insert("INSERT INTO comprehensive_vocabulary_test_answer (user_answer, word, type, definition, options, last_modified, is_deleted, is_correct, comprehensive_test_results_id) " +
            "VALUES (#{user_answer}, #{word}, #{type}, #{definition}, #{options}, #{last_modified}, #{is_deleted}, #{is_correct}, #{comprehensiveTestResults.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertComprehensiveVocabularyTestAnswer(ComprehensiveVocabularyTestAnswer answer);

    // Update an existing ComprehensiveVocabularyTestAnswer entry
    @Update("UPDATE comprehensive_vocabulary_test_answer SET user_answer = #{user_answer}, word = #{word}, type = #{type}, definition = #{definition}, " +
            "options = #{options}, last_modified = #{last_modified}, is_deleted = #{is_deleted}, is_correct = #{is_correct}, comprehensive_test_results_id = #{comprehensiveTestResults.id} " +
            "WHERE id = #{id}")
    void updateComprehensiveVocabularyTestAnswer(ComprehensiveVocabularyTestAnswer answer);

    // Soft delete a ComprehensiveVocabularyTestAnswer entry
    @Update("UPDATE comprehensive_vocabulary_test_answer SET is_deleted = true, last_modified = NOW() WHERE id = #{id}")
    void softDeleteComprehensiveVocabularyTestAnswer(Integer id);

    // Permanently delete a ComprehensiveVocabularyTestAnswer entry
    @Delete("DELETE FROM comprehensive_vocabulary_test_answer WHERE id = #{id}")
    void deleteComprehensiveVocabularyTestAnswerPermanently(Integer id);

    // Retrieve a ComprehensiveVocabularyTestAnswer entry by ID
    @Select("SELECT * FROM comprehensive_vocabulary_test_answer WHERE id = #{id} AND is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user_answer", column = "user_answer"),
            @Result(property = "word", column = "word"),
            @Result(property = "type", column = "type"),
            @Result(property = "definition", column = "definition"),
            @Result(property = "options", column = "options"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "is_correct", column = "is_correct"),
            @Result(property = "comprehensiveTestResults.id", column = "comprehensive_test_results_id")
    })
    ComprehensiveVocabularyTestAnswer getComprehensiveVocabularyTestAnswerById(Integer id);

    // Retrieve all active ComprehensiveVocabularyTestAnswer entries
    @Select("SELECT * FROM comprehensive_vocabulary_test_answer WHERE is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user_answer", column = "user_answer"),
            @Result(property = "word", column = "word"),
            @Result(property = "type", column = "type"),
            @Result(property = "definition", column = "definition"),
            @Result(property = "options", column = "options"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "is_correct", column = "is_correct"),
            @Result(property = "comprehensiveTestResults.id", column = "comprehensive_test_results_id")
    })
    List<ComprehensiveVocabularyTestAnswer> getAllActiveComprehensiveVocabularyTestAnswers();

    // Retrieve ComprehensiveVocabularyTestAnswer entries by test ID
    @Select("SELECT * FROM comprehensive_vocabulary_test_answer WHERE comprehensive_test_results_id = #{testId} AND is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user_answer", column = "user_answer"),
            @Result(property = "word", column = "word"),
            @Result(property = "type", column = "type"),
            @Result(property = "definition", column = "definition"),
            @Result(property = "options", column = "options"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "is_correct", column = "is_correct"),
            @Result(property = "comprehensiveTestResults.id", column = "comprehensive_test_results_id")
    })
    List<ComprehensiveVocabularyTestAnswer> getComprehensiveVocabularyTestAnswersByTestId(@Param("testId") Integer testId);
}
