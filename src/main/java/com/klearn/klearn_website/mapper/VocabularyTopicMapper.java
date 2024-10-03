package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.VocabularyTopic;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface VocabularyTopicMapper {

  @Insert("INSERT INTO vocabulary_topic (course_id, topic_name, topic_description, topic_image, created_at, last_modified, is_deleted) " +
          "VALUES (#{course.id}, #{topic_name}, #{topic_description}, #{topic_image}, #{created_at}, #{last_modified}, #{is_deleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertVocabularyTopic(VocabularyTopic vocabularyTopic); 

  @Select("SELECT vt.*, c.* FROM vocabulary_topic vt " +
          "JOIN courses c ON vt.course_id = c.id " +
          "WHERE vt.is_deleted = 0")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "topic_name", column = "topic_name"),
      @Result(property = "topic_description", column = "topic_description"),
      @Result(property = "topic_image", column = "topic_image"),
      @Result(property = "created_at", column = "created_at"),
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
  List<VocabularyTopic> getAll();
  
  @Select("SELECT vt.*, c.* FROM vocabulary_topic vt " +
          "JOIN courses c ON vt.course_id = c.id " +
          "WHERE vt.course_id = #{course_id} AND vt.is_deleted = 0")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "topic_name", column = "topic_name"),
      @Result(property = "topic_description", column = "topic_description"),
      @Result(property = "topic_image", column = "topic_image"),
      @Result(property = "created_at", column = "created_at"),
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
  List<VocabularyTopic> getAllByCourseId(@Param("course_id") Integer courseId);
}
