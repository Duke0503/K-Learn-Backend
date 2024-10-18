package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.GrammarProgress;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface GrammarProgressMapper {

    // Insert a new GrammarProgress entry
    @Insert("INSERT INTO grammar_progress (user_id, grammar_id, course_id, is_learned_theory, is_finish_quiz, last_modified, is_deleted) "
            + "VALUES (#{id.user_id}, #{id.grammar_id}, #{id.course_id}, #{is_learned_theory}, #{is_finish_quiz}, #{last_modified}, #{is_deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertGrammarProgress(GrammarProgress grammarProgress);

    // Retrieve all GrammarProgress entries for a specific user and course that are not deleted
    @Select("SELECT * FROM grammar_progress WHERE user_id = #{user_id} AND course_id = #{course_id} AND is_deleted = 0")
    @Results({
            @Result(property = "id.user_id", column = "user_id"),
            @Result(property = "id.grammar_id", column = "grammar_id"),
            @Result(property = "id.course_id", column = "course_id"),
            @Result(property = "is_learned_theory", column = "is_learned_theory"),
            @Result(property = "is_finish_quiz", column = "is_finish_quiz"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "course.id", column = "course_id")
    })
    List<GrammarProgress> getGrammarProgressByUserIdAndCourseId(@Param("user_id") Integer userId,
                                                                @Param("course_id") Integer courseId);

    // Retrieve a single GrammarProgress entry for a user and grammar that is not deleted
    @Select("SELECT * FROM grammar_progress WHERE user_id = #{user_id} AND grammar_id = #{grammar_id} AND is_deleted = 0")
    @Results({
            @Result(property = "id.user_id", column = "user_id"),
            @Result(property = "id.grammar_id", column = "grammar_id"),
            @Result(property = "id.course_id", column = "course_id"),
            @Result(property = "is_learned_theory", column = "is_learned_theory"),
            @Result(property = "is_finish_quiz", column = "is_finish_quiz"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "course.id", column = "course_id")
    })
    GrammarProgress getGrammarProgressByUserIdAndGrammarId(@Param("user_id") Integer userId,
                                                           @Param("grammar_id") Integer grammarId);

    // Update an existing GrammarProgress entry
    @Update("UPDATE grammar_progress SET is_learned_theory = #{is_learned_theory}, is_finish_quiz = #{is_finish_quiz}, last_modified = #{last_modified}, is_deleted = #{is_deleted} " +
            "WHERE user_id = #{id.user_id} AND grammar_id = #{id.grammar_id} AND course_id = #{id.course_id} AND is_deleted = 0")
    void updateGrammarProgress(GrammarProgress grammarProgress);

    // Soft delete a GrammarProgress entry by setting is_deleted to true
    @Update("UPDATE grammar_progress SET is_deleted = 1, last_modified = NOW() WHERE user_id = #{user_id} AND grammar_id = #{grammar_id} AND course_id = #{course_id}")
    void softDeleteGrammarProgress(@Param("user_id") Integer userId, @Param("grammar_id") Integer grammarId,
                                   @Param("course_id") Integer courseId);

    // Permanently delete a GrammarProgress entry from the database
    @Delete("DELETE FROM grammar_progress WHERE user_id = #{user_id} AND grammar_id = #{grammar_id} AND course_id = #{course_id}")
    void deleteGrammarProgressPermanently(@Param("user_id") Integer userId, @Param("grammar_id") Integer grammarId,
                                          @Param("course_id") Integer courseId);

    // Count the number of learned grammar entries for a user and course
    @Select("SELECT COUNT(*) FROM grammar_progress WHERE user_id = #{user_id} AND course_id = #{course_id} AND is_learned_theory = true AND is_finish_quiz = true AND is_deleted = 0")
    int countLearnedGrammar(@Param("user_id") Integer userId, @Param("course_id") Integer courseId);

    // Count the number of not learned grammar entries for a user and course
    @Select("SELECT COUNT(*) FROM grammar_progress WHERE user_id = #{user_id} AND course_id = #{course_id} AND (is_learned_theory = false OR is_finish_quiz = false) AND is_deleted = 0")
    int countNotLearnedGrammar(@Param("user_id") Integer userId, @Param("course_id") Integer courseId);

    // Mark a grammar entry as learned
    @Update("UPDATE grammar_progress SET is_learned_theory = true, last_modified = NOW() WHERE user_id = #{user_id} AND grammar_id = #{grammar_id} AND course_id = #{course_id} AND is_deleted = 0")
    void markGrammarTheoryAsLearned(@Param("user_id") Integer userId, @Param("grammar_id") Integer grammarId,
                                    @Param("course_id") Integer courseId);

    // Mark a grammar quiz as finished
    @Update("UPDATE grammar_progress SET is_finish_quiz = true, last_modified = NOW() WHERE user_id = #{user_id} AND grammar_id = #{grammar_id} AND course_id = #{course_id} AND is_deleted = 0")
    void markGrammarQuizAsFinished(@Param("user_id") Integer userId, @Param("grammar_id") Integer grammarId,
                                   @Param("course_id") Integer courseId);

    // Check if a GrammarProgress entry exists for a user, grammar, and course
    @Select("SELECT COUNT(*) > 0 FROM grammar_progress WHERE user_id = #{user_id} AND grammar_id = #{grammar_id} AND course_id = #{course_id} AND is_deleted = 0")
    boolean existsByUserIdAndGrammarIdAndCourseId(@Param("user_id") Integer userId,
                                                  @Param("grammar_id") Integer grammarId, @Param("course_id") Integer courseId);

    // Retrieve all GrammarProgress entries that are not deleted
    @Select("SELECT * FROM grammar_progress WHERE is_deleted = 0")
    @Results({
            @Result(property = "id.user_id", column = "user_id"),
            @Result(property = "id.grammar_id", column = "grammar_id"),
            @Result(property = "id.course_id", column = "course_id"),
            @Result(property = "is_learned_theory", column = "is_learned_theory"),
            @Result(property = "is_finish_quiz", column = "is_finish_quiz"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "grammar.id", column = "grammar_id"),
            @Result(property = "course.id", column = "course_id")
    })
    List<GrammarProgress> getAllActiveGrammarProgress();
}
