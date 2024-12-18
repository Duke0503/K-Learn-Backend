package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.QuestionGrammar;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionGrammarMapper {

    // Insert a new QuestionGrammar entry
    @Insert("INSERT INTO question_grammar (question_text, correct_answer, incorrect_answer, quiz_type, created_at, last_modified, is_deleted, grammar_id) "
            + "VALUES (#{question_text}, #{correct_answer}, #{incorrect_answer}, #{quiz_type}, #{created_at}, #{last_modified}, #{is_deleted}, #{grammar.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertQuestionGrammar(QuestionGrammar questionGrammar);

    // Update an existing QuestionGrammar entry
    @Update("UPDATE question_grammar SET question_text = #{question_text}, correct_answer = #{correct_answer}, incorrect_answer = #{incorrect_answer}, quiz_type = #{quiz_type}, last_modified = #{last_modified}, is_deleted = #{is_deleted}, grammar_id = #{grammar.id} "
            + "WHERE id = #{id}")
    void updateQuestionGrammar(QuestionGrammar questionGrammar);

    // Soft delete a QuestionGrammar entry
    @Update("UPDATE question_grammar SET is_deleted = 1, last_modified = NOW() WHERE id = #{id}")
    void softDeleteQuestionGrammar(Integer id);

    // Permanently delete a QuestionGrammar entry
    @Delete("DELETE FROM question_grammar WHERE id = #{id}")
    void deleteQuestionGrammarPermanently(Integer id);

    // Get all QuestionGrammar entries by Grammar ID including Grammar details
    @Select("SELECT q.*, g.*, c.* FROM question_grammar q " +
            "JOIN grammar g ON q.grammar_id = g.id " +
            "JOIN courses c ON g.course_id = c.id " +
            "WHERE q.grammar_id = #{grammar_id} AND q.is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "question_text", column = "question_text"),
            @Result(property = "correct_answer", column = "correct_answer"),
            @Result(property = "incorrect_answer", column = "incorrect_answer"),
            @Result(property = "quiz_type", column = "quiz_type"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            // Map the Grammar object
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "grammar.grammar_name", column = "grammar_name"),
            @Result(property = "grammar.grammar_description", column = "grammar_description"),
            @Result(property = "grammar.explanation", column = "explanation"),
            @Result(property = "grammar.example", column = "example"),
            @Result(property = "grammar.lesson_number", column = "lesson_number"),
            @Result(property = "grammar.last_modified", column = "g.last_modified"),
            @Result(property = "grammar.is_deleted", column = "g.is_deleted"),
            // Map the Course object
            @Result(property = "grammar.course.id", column = "course_id"),
            @Result(property = "grammar.course.course_name", column = "course_name"),
            @Result(property = "grammar.course.course_level", column = "course_level"),
            @Result(property = "grammar.course.course_description", column = "course_description"),
            @Result(property = "grammar.course.course_image", column = "course_image"),
            @Result(property = "grammar.course.course_price", column = "course_price"),
            @Result(property = "grammar.course.created_at", column = "c.created_at"),
            @Result(property = "grammar.course.last_modified", column = "c.last_modified"),
            @Result(property = "grammar.course.is_deleted", column = "c.is_deleted")
    })
    List<QuestionGrammar> getQuestionsByGrammarId(@Param("grammar_id") Integer grammarId);

    @Select("SELECT q.*, g.*, c.* " +
            "FROM question_grammar q " +
            "JOIN grammar g ON q.grammar_id = g.id " +
            "JOIN courses c ON g.course_id = c.id " +
            "WHERE q.id = #{id} AND q.is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "question_text", column = "question_text"),
            @Result(property = "correct_answer", column = "correct_answer"),
            @Result(property = "incorrect_answer", column = "incorrect_answer"),
            @Result(property = "quiz_type", column = "quiz_type"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),

            // Map Grammar object
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "grammar.grammar_name", column = "grammar_name"),
            @Result(property = "grammar.grammar_description", column = "grammar_description"),
            @Result(property = "grammar.explanation", column = "explanation"),
            @Result(property = "grammar.example", column = "example"),
            @Result(property = "grammar.lesson_number", column = "lesson_number"),
            @Result(property = "grammar.last_modified", column = "g.last_modified"),
            @Result(property = "grammar.is_deleted", column = "g.is_deleted"),

            // Map Course object inside Grammar
            @Result(property = "grammar.course.id", column = "course_id"),
            @Result(property = "grammar.course.course_name", column = "course_name"),
            @Result(property = "grammar.course.course_level", column = "course_level"),
            @Result(property = "grammar.course.course_description", column = "course_description"),
            @Result(property = "grammar.course.course_image", column = "course_image"),
            @Result(property = "grammar.course.course_price", column = "course_price"),
            @Result(property = "grammar.course.created_at", column = "c.created_at"),
            @Result(property = "grammar.course.last_modified", column = "c.last_modified"),
            @Result(property = "grammar.course.is_deleted", column = "c.is_deleted")
    })
    QuestionGrammar getQuestionById(@Param("id") Integer id);

    // Check if a QuestionGrammar entry exists by grammar ID
    @Select("SELECT COUNT(*) > 0 FROM question_grammar WHERE grammar_id = #{grammar_id} AND is_deleted = 0")
    boolean existsByGrammarId(@Param("grammar_id") Integer grammarId);

    // Get all active QuestionGrammar entries
    @Select("SELECT q.*, g.*, c.* FROM question_grammar q " +
            "JOIN grammar g ON q.grammar_id = g.id " +
            "JOIN courses c ON g.course_id = c.id " +
            "WHERE q.is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "question_text", column = "question_text"),
            @Result(property = "correct_answer", column = "correct_answer"),
            @Result(property = "incorrect_answer", column = "incorrect_answer"),
            @Result(property = "quiz_type", column = "quiz_type"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            // Map the Grammar object
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "grammar.grammar_name", column = "grammar_name"),
            @Result(property = "grammar.grammar_description", column = "grammar_description"),
            @Result(property = "grammar.explanation", column = "explanation"),
            @Result(property = "grammar.example", column = "example"),
            @Result(property = "grammar.lesson_number", column = "lesson_number"),
            @Result(property = "grammar.last_modified", column = "g.last_modified"),
            @Result(property = "grammar.is_deleted", column = "g.is_deleted"),
            // Map the Course object
            @Result(property = "grammar.course.id", column = "course_id"),
            @Result(property = "grammar.course.course_name", column = "course_name"),
            @Result(property = "grammar.course.course_level", column = "course_level"),
            @Result(property = "grammar.course.course_description", column = "course_description"),
            @Result(property = "grammar.course.course_image", column = "course_image"),
            @Result(property = "grammar.course.course_price", column = "course_price"),
            @Result(property = "grammar.course.created_at", column = "c.created_at"),
            @Result(property = "grammar.course.last_modified", column = "c.last_modified"),
            @Result(property = "grammar.course.is_deleted", column = "c.is_deleted")
    })
    List<QuestionGrammar> getAllActiveQuestions();
}
