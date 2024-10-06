package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.Grammar;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface GrammarMapper {
  @Insert("INSERT INTO grammar (course_id, grammar_name, grammar_description, explanation, example, lesson_number, last_modified, is_deleted) " +
          "VALUES (#{course.id}, #{grammar_name}, #{grammar_description}, #{explanation}, #{example}, #{lesson_number}, #{last_modified}, #{is_deleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertGrammar(Grammar grammar); 
  
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
}
