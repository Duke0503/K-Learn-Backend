package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.VocabularyProgress;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface VocabularyProgressMapper {

  // Insert a new VocabularyProgress record
  @Insert("INSERT INTO vocabulary_progress (user_id, vocabulary_id, topic_id, is_learned, last_modified, is_deleted) " +
          "VALUES (#{id.user_id}, #{id.vocabulary_id}, #{id.topic_id}, #{is_learned}, #{last_modified}, #{is_deleted})")
  void insertVocabularyProgress(VocabularyProgress vocabularyProgress);

  // Fetch VocabularyProgress by user_id and topic_id with joined users and vocabulary_topic tables
  @Select("SELECT vp.user_id, vp.vocabulary_id, vp.topic_id, vp.is_learned, vp.last_modified, vp.is_deleted, " +
          "v.id AS vocabulary_id, v.word, v.definition, v.transcription, v.image, v.last_modified AS vocabulary_last_modified, v.is_deleted AS vocabulary_is_deleted, " +
          "u.id AS user_id, u.username, " +
          "vt.id AS topic_id, vt.topic_name, vt.topic_description " +
          "FROM vocabulary_progress vp " +
          "JOIN vocabulary v ON vp.vocabulary_id = v.id " +
          "JOIN users u ON vp.user_id = u.id " +
          "JOIN vocabulary_topic vt ON vp.topic_id = vt.id " +
          "WHERE vp.user_id = #{user_id} AND vp.topic_id = #{topic_id} AND vp.is_deleted = 0")
  @Results({
    // Mapping VocabularyProgress fields
    @Result(property = "id.user_id", column = "user_id"),
    @Result(property = "id.vocabulary_id", column = "vocabulary_id"),
    @Result(property = "id.topic_id", column = "topic_id"),
    @Result(property = "is_learned", column = "is_learned"),
    @Result(property = "last_modified", column = "last_modified"),
    @Result(property = "is_deleted", column = "is_deleted"),

    // Map Vocabulary data
    @Result(property = "vocabulary.id", column = "vocabulary_id"),
    @Result(property = "vocabulary.word", column = "word"),
    @Result(property = "vocabulary.definition", column = "definition"),
    @Result(property = "vocabulary.transcription", column = "transcription"),
    @Result(property = "vocabulary.image", column = "image"),
    @Result(property = "vocabulary.last_modified", column = "vocabulary_last_modified"),
    @Result(property = "vocabulary.is_deleted", column = "vocabulary_is_deleted"),

    // Map User data
    @Result(property = "user.id", column = "user_id"),
    @Result(property = "user.username", column = "username"),

    // Map VocabularyTopic data
    @Result(property = "vocabularyTopic.id", column = "topic_id"),
    @Result(property = "vocabularyTopic.topic_name", column = "topic_name"),
    @Result(property = "vocabularyTopic.topic_description", column = "topic_description")
  })
  List<VocabularyProgress> getVocabularyProgressByUserIdAndTopicId(@Param("user_id") Integer userId, @Param("topic_id") Integer topicId);

  // Check if VocabularyProgress exists by user_id, topic_id, and vocabulary_id
  @Select("SELECT COUNT(1) FROM vocabulary_progress WHERE user_id = #{user_id} AND topic_id = #{topic_id} AND vocabulary_id = #{vocabulary_id} AND is_deleted = 0")
  boolean existsByUserIdAndTopicId(@Param("user_id") Integer userId, @Param("topic_id") Integer topicId, @Param("vocabulary_id") Integer vocabularyId);
}
