package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.QuestionGrammar;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionGrammarMapper {

    @Insert("INSERT INTO question_grammar (question_text, correct_answer, incorrect_answer, quiz_type, created_at, last_modified, is_deleted, grammar_id) " +
            "VALUES (#{question_text}, #{correct_answer}, #{incorrect_answer}, #{quiz_type}, #{created_at}, #{last_modified}, #{is_deleted}, #{grammar.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertQuestionGrammar(QuestionGrammar questionGrammar);

    @Update("UPDATE question_grammar SET is_deleted = 1 WHERE id = #{id}")
    void softDeleteQuestionGrammar(Integer id);
  
    @Select("SELECT * FROM question_grammar WHERE grammar_id = #{grammar_id} AND is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "question_text", column = "question_text"),
            @Result(property = "correct_answer", column = "correct_answer"),
            @Result(property = "incorrect_answer", column = "incorrect_answer"),
            @Result(property = "quiz_type", column = "quiz_type"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "grammar.id", column = "grammar_id")
    })
    List<QuestionGrammar> getQuestionsByGrammarId(@Param("grammar_id") Integer grammarId);

    @Select("SELECT COUNT(*) > 0 FROM question_grammar WHERE grammar_id = #{grammar_id} AND is_deleted = 0")
    boolean existsByGrammarId(@Param("grammar_id") Integer grammarId);
}
