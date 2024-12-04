package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.Grammar;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface GrammarMapper {

    // Create a new Grammar entry
    @Insert("INSERT INTO grammar (course_id, grammar_name, grammar_description, explanation, example, lesson_number, last_modified, is_deleted) "
            + "VALUES (#{course.id}, #{grammar_name}, #{grammar_description}, #{explanation}, #{example}, #{lesson_number}, #{last_modified}, #{is_deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertGrammar(Grammar grammar);

    // Retrieve all Grammar entries that are not deleted
    @Select("SELECT g.*, c.* FROM grammar g " +
            "JOIN courses c ON g.course_id = c.id " +
            "WHERE g.is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "grammar_name", column = "grammar_name"),
            @Result(property = "grammar_description", column = "grammar_description"),
            @Result(property = "explanation", column = "explanation"),
            @Result(property = "example", column = "example"),
            @Result(property = "lesson_number", column = "lesson_number"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            // Map the course object
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_description", column = "course_description"),
            @Result(property = "course.course_image", column = "course_image"),
            @Result(property = "course.course_price", column = "course_price"),
            @Result(property = "course.created_at", column = "created_at"),
            @Result(property = "course.last_modified", column = "last_modified"),
            @Result(property = "course.is_deleted", column = "is_deleted")
    })
    List<Grammar> getAllGrammar();

    // Retrieve all Grammar entries by Course ID
    @Select("SELECT g.*, c.* FROM grammar g " +
            "JOIN courses c ON g.course_id = c.id " +
            "WHERE g.course_id = #{course_id} AND g.is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "grammar_name", column = "grammar_name"),
            @Result(property = "grammar_description", column = "grammar_description"),
            @Result(property = "explanation", column = "explanation"),
            @Result(property = "example", column = "example"),
            @Result(property = "lesson_number", column = "lesson_number"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            // Map the course object
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_description", column = "course_description"),
            @Result(property = "course.course_image", column = "course_image"),
            @Result(property = "course.course_price", column = "course_price"),
            @Result(property = "course.created_at", column = "created_at"),
            @Result(property = "course.last_modified", column = "last_modified"),
            @Result(property = "course.is_deleted", column = "is_deleted")
    })
    List<Grammar> getAllByCourseId(@Param("course_id") Integer courseId);

    // Retrieve all Grammar entries by Course ID
    @Select("SELECT g.*, c.* FROM grammar g " +
            "JOIN courses c ON g.course_id = c.id " +
            "WHERE g.id = #{grammar_id} AND g.is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "grammar_name", column = "grammar_name"),
            @Result(property = "grammar_description", column = "grammar_description"),
            @Result(property = "explanation", column = "explanation"),
            @Result(property = "example", column = "example"),
            @Result(property = "lesson_number", column = "lesson_number"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),
            // Map the course object
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_description", column = "course_description"),
            @Result(property = "course.course_image", column = "course_image"),
            @Result(property = "course.course_price", column = "course_price"),
            @Result(property = "course.created_at", column = "created_at"),
            @Result(property = "course.last_modified", column = "last_modified"),
            @Result(property = "course.is_deleted", column = "is_deleted")
    })
    Grammar getGrammarById(@Param("grammar_id") Integer grammarId);

    // Update an existing Grammar entry
    @Update("UPDATE grammar SET course_id = #{course.id}, grammar_name = #{grammar_name}, grammar_description = #{grammar_description}, " +
            "explanation = #{explanation}, example = #{example}, lesson_number = #{lesson_number}, last_modified = #{last_modified}, is_deleted = #{is_deleted} " +
            "WHERE id = #{id} AND is_deleted = 0")
    void updateGrammar(Grammar grammar);

    // Soft delete a Grammar entry by setting is_deleted to true
    @Update("UPDATE grammar SET is_deleted = 1, last_modified = NOW() WHERE id = #{grammar_id}")
    void softDeleteGrammar(@Param("grammar_id") Integer grammarId);

    // Permanently delete a Grammar entry
    @Delete("DELETE FROM grammar WHERE id = #{grammar_id}")
    void deleteGrammarPermanently(@Param("grammar_id") Integer grammarId);
}
