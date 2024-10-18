package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.VocabularyProgress;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VocabularyProgressMapper {

    // Create a new vocabulary progress entry
    @Insert("INSERT INTO vocabulary_progress (user_id, vocabulary_id, topic_id, is_learned, last_modified, is_deleted) "
            +
            "VALUES (#{id.user_id}, #{id.vocabulary_id}, #{id.topic_id}, #{is_learned}, #{last_modified}, #{is_deleted})")
    void insertVocabularyProgress(VocabularyProgress vocabularyProgress);

    // Read vocabulary progress entries by user ID and topic ID
    @Select("SELECT vp.user_id, vp.vocabulary_id, vp.topic_id, vp.is_learned, vp.last_modified, vp.is_deleted, " +
            "v.id AS vocabulary_id, v.word, v.definition, v.transcription, v.image, v.last_modified AS vocabulary_last_modified, v.is_deleted AS vocabulary_is_deleted, "
            +
            "u.id AS user_id, u.username, " +
            "vt.id AS topic_id, vt.topic_name, vt.topic_description " +
            "FROM vocabulary_progress vp " +
            "JOIN vocabulary v ON vp.vocabulary_id = v.id " +
            "JOIN users u ON vp.user_id = u.id " +
            "JOIN vocabulary_topic vt ON vp.topic_id = vt.id " +
            "WHERE vp.user_id = #{user_id} AND vp.topic_id = #{topic_id} AND vp.is_deleted = 0")
    @Results({
            @Result(property = "id.user_id", column = "user_id"),
            @Result(property = "id.vocabulary_id", column = "vocabulary_id"),
            @Result(property = "id.topic_id", column = "topic_id"),
            @Result(property = "is_learned", column = "is_learned"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),

            @Result(property = "vocabulary.id", column = "vocabulary_id"),
            @Result(property = "vocabulary.word", column = "word"),
            @Result(property = "vocabulary.definition", column = "definition"),
            @Result(property = "vocabulary.transcription", column = "transcription"),
            @Result(property = "vocabulary.image", column = "image"),
            @Result(property = "vocabulary.last_modified", column = "vocabulary_last_modified"),
            @Result(property = "vocabulary.is_deleted", column = "vocabulary_is_deleted"),

            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.username", column = "username"),

            @Result(property = "vocabularyTopic.id", column = "topic_id"),
            @Result(property = "vocabularyTopic.topic_name", column = "topic_name"),
            @Result(property = "vocabularyTopic.topic_description", column = "topic_description")
    })
    List<VocabularyProgress> getVocabularyProgressByUserIdAndTopicId(@Param("user_id") Integer userId,
            @Param("topic_id") Integer topicId);

    // Read vocabulary progress entry by ID (Composite key)
    @Select("SELECT * FROM vocabulary_progress WHERE user_id = #{user_id} AND vocabulary_id = #{vocabulary_id} AND topic_id = #{topic_id} AND is_deleted = 0")
    VocabularyProgress findVocabularyProgressById(@Param("user_id") Integer userId,
            @Param("vocabulary_id") Integer vocabularyId,
            @Param("topic_id") Integer topicId);

    // Check if a vocabulary progress entry exists
    @Select("SELECT COUNT(1) FROM vocabulary_progress WHERE user_id = #{user_id} AND topic_id = #{topic_id} AND vocabulary_id = #{vocabulary_id} AND is_deleted = 0")
    boolean existsByUserIdAndTopicId(@Param("user_id") Integer userId, @Param("topic_id") Integer topicId,
            @Param("vocabulary_id") Integer vocabularyId);

    // Update vocabulary progress entry by ID
    @Update("UPDATE vocabulary_progress " +
            "SET is_learned = #{is_learned}, last_modified = NOW(), is_deleted = #{is_deleted} " +
            "WHERE user_id = #{id.user_id} AND vocabulary_id = #{id.vocabulary_id} AND topic_id = #{id.topic_id}")
    void updateVocabularyProgress(VocabularyProgress vocabularyProgress);

    // Mark a vocabulary entry as learned
    @Update("UPDATE vocabulary_progress " +
            "SET is_learned = true, last_modified = NOW() " +
            "WHERE user_id = #{user_id} AND topic_id = #{topic_id} AND vocabulary_id = #{vocabulary_id} AND is_deleted = 0")
    void markVocabularyAsLearned(@Param("user_id") Integer userId, @Param("topic_id") Integer topicId,
            @Param("vocabulary_id") Integer vocabularyId);

    // Count vocabulary entries that are not learned
    @Select("SELECT COUNT(*) FROM vocabulary_progress " +
            "WHERE user_id = #{user_id} AND topic_id = #{topic_id} AND is_deleted = 0 " +
            "AND is_learned = 0")
    Integer countVocabularyNotLearned(@Param("user_id") Integer userId, @Param("topic_id") Integer topicId);

    // Count vocabulary entries that are learned
    @Select("SELECT COUNT(*) FROM vocabulary_progress " +
            "WHERE user_id = #{user_id} AND topic_id = #{topic_id} AND is_deleted = 0 " +
            "AND is_learned = 1")
    Integer countVocabularyLearned(@Param("user_id") Integer userId, @Param("topic_id") Integer topicId);

    // Soft delete a vocabulary progress entry by setting is_deleted to 1
    @Update("UPDATE vocabulary_progress SET is_deleted = 1, last_modified = NOW() " +
            "WHERE user_id = #{user_id} AND vocabulary_id = #{vocabulary_id} AND topic_id = #{topic_id}")
    void softDeleteVocabularyProgress(@Param("user_id") Integer userId, @Param("vocabulary_id") Integer vocabularyId,
            @Param("topic_id") Integer topicId);

    // Permanently delete a vocabulary progress entry
    @Delete("DELETE FROM vocabulary_progress WHERE user_id = #{user_id} AND vocabulary_id = #{vocabulary_id} AND topic_id = #{topic_id}")
    void deleteVocabularyProgressPermanently(@Param("user_id") Integer userId,
            @Param("vocabulary_id") Integer vocabularyId,
            @Param("topic_id") Integer topicId);
}
