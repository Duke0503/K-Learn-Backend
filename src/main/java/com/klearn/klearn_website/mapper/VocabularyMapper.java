package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.Vocabulary;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface VocabularyMapper {
  @Insert("INSERT INTO vocabulary (topic_id, word, definition, transcription, image, last_modified, is_deleted) " +
          "VALUES (#{vocabularyTopic.id}, #{word}, #{definition}, #{transcription}, #{image}, #{last_modified}, #{is_deleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void createVocabulary(Vocabulary vocabulary);
  
  @Select("SELECT v.*, vt.* FROM vocabulary v " +
        "JOIN vocabulary_topic vt ON v.topic_id = vt.id " +
        "WHERE v.topic_id = #{topic_id} AND v.is_deleted = 0")
  @Results({
    @Result(property = "id", column = "id"),
    @Result(property = "word", column = "word"),
    @Result(property = "definition", column = "definition"),
    @Result(property = "transcription", column = "transcription"),
    @Result(property = "image", column = "image"),
    @Result(property = "last_modified", column = "last_modified"),
    @Result(property = "is_deleted", column = "is_deleted"),
    // Map the course object
    @Result(property = "vocabularyTopic.id", column = "topic_id"),
    @Result(property = "vocabularyTopic.topic_name", column = "topic_name"),
    @Result(property = "vocabularyTopic.topic_description", column = "topic_description"),
    @Result(property = "vocabularyTopic.topic_image", column = "topic_image"),
    @Result(property = "vocabularyTopic.created_at", column = "created_at"),
    @Result(property = "vocabularyTopic.last_modified", column = "last_modified"),
    @Result(property = "vocabularyTopic.is_deleted", column = "is_deleted")
  })
  List<Vocabulary> getVocabularyByTopicId(@Param("topic_id") Integer topic_id);
}

