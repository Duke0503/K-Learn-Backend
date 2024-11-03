package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.Vocabulary;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface VocabularyMapper {

    // Insert a new vocabulary entry
    @Insert("INSERT INTO vocabulary (topic_id, word, definition, transcription, image, last_modified, is_deleted) " +
            "VALUES (#{vocabularyTopic.id}, #{word}, #{definition}, #{transcription}, #{image}, #{last_modified}, #{is_deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createVocabulary(Vocabulary vocabulary);

    // Get vocabulary by topic ID that is not deleted
    @Select("SELECT v.id, v.word, v.definition, v.transcription, v.image, v.last_modified, v.is_deleted, " +
            "vt.id as topic_id, vt.topic_name, vt.topic_description, vt.topic_image, vt.created_at, vt.last_modified as topic_last_modified, vt.is_deleted as topic_is_deleted, "
            +
            "c.id as course_id, c.course_name, c.course_level, c.course_description, c.course_image, c.course_price, c.created_at as course_created_at, c.last_modified as course_last_modified, c.is_deleted as course_is_deleted "
            +
            "FROM vocabulary v " +
            "JOIN vocabulary_topic vt ON v.topic_id = vt.id " +
            "JOIN courses c ON vt.course_id = c.id " +
            "WHERE v.topic_id = #{topic_id} AND v.is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "word", column = "word"),
            @Result(property = "definition", column = "definition"),
            @Result(property = "transcription", column = "transcription"),
            @Result(property = "image", column = "image"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),

            @Result(property = "vocabularyTopic.id", column = "topic_id"),
            @Result(property = "vocabularyTopic.topic_name", column = "topic_name"),
            @Result(property = "vocabularyTopic.topic_description", column = "topic_description"),
            @Result(property = "vocabularyTopic.topic_image", column = "topic_image"),
            @Result(property = "vocabularyTopic.created_at", column = "created_at"),
            @Result(property = "vocabularyTopic.last_modified", column = "topic_last_modified"),
            @Result(property = "vocabularyTopic.is_deleted", column = "topic_is_deleted"),

            @Result(property = "vocabularyTopic.course.id", column = "course_id"),
            @Result(property = "vocabularyTopic.course.course_name", column = "course_name"),
            @Result(property = "vocabularyTopic.course.course_level", column = "course_level"),
            @Result(property = "vocabularyTopic.course.course_description", column = "course_description"),
            @Result(property = "vocabularyTopic.course.course_image", column = "course_image"),
            @Result(property = "vocabularyTopic.course.course_price", column = "course_price"),
            @Result(property = "vocabularyTopic.course.created_at", column = "course_created_at"),
            @Result(property = "vocabularyTopic.course.last_modified", column = "course_last_modified"),
            @Result(property = "vocabularyTopic.course.is_deleted", column = "course_is_deleted")
    })
    List<Vocabulary> getVocabularyByTopicId(@Param("topic_id") Integer topic_id);

    // Get vocabulary by topic ID that is not deleted
    @Select("SELECT v.id, v.word, v.definition, v.transcription, v.image, v.last_modified, v.is_deleted, " +
            "vt.id as topic_id, vt.topic_name, vt.topic_description, vt.topic_image, vt.created_at, vt.last_modified as topic_last_modified, vt.is_deleted as topic_is_deleted, "
            +
            "c.id as course_id, c.course_name, c.course_level, c.course_description, c.course_image, c.course_price, c.created_at as course_created_at, c.last_modified as course_last_modified, c.is_deleted as course_is_deleted "
            +
            "FROM vocabulary v " +
            "JOIN vocabulary_topic vt ON v.topic_id = vt.id " +
            "JOIN courses c ON vt.course_id = c.id " +
            "WHERE v.is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "word", column = "word"),
            @Result(property = "definition", column = "definition"),
            @Result(property = "transcription", column = "transcription"),
            @Result(property = "image", column = "image"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),

            @Result(property = "vocabularyTopic.id", column = "topic_id"),
            @Result(property = "vocabularyTopic.topic_name", column = "topic_name"),
            @Result(property = "vocabularyTopic.topic_description", column = "topic_description"),
            @Result(property = "vocabularyTopic.topic_image", column = "topic_image"),
            @Result(property = "vocabularyTopic.created_at", column = "created_at"),
            @Result(property = "vocabularyTopic.last_modified", column = "topic_last_modified"),
            @Result(property = "vocabularyTopic.is_deleted", column = "topic_is_deleted"),

            @Result(property = "vocabularyTopic.course.id", column = "course_id"),
            @Result(property = "vocabularyTopic.course.course_name", column = "course_name"),
            @Result(property = "vocabularyTopic.course.course_level", column = "course_level"),
            @Result(property = "vocabularyTopic.course.course_description", column = "course_description"),
            @Result(property = "vocabularyTopic.course.course_image", column = "course_image"),
            @Result(property = "vocabularyTopic.course.course_price", column = "course_price"),
            @Result(property = "vocabularyTopic.course.created_at", column = "course_created_at"),
            @Result(property = "vocabularyTopic.course.last_modified", column = "course_last_modified"),
            @Result(property = "vocabularyTopic.course.is_deleted", column = "course_is_deleted")
    })
    List<Vocabulary> getAllVocabulary();

    // Get vocabulary by ID
    @Select("SELECT * FROM vocabulary WHERE id = #{id} AND is_deleted = 0")
    Vocabulary getVocabularyById(@Param("id") Integer id);

    // Count vocabulary by topic ID that is not deleted
    @Select("SELECT COUNT(*) FROM vocabulary WHERE topic_id = #{topic_id} AND is_deleted = 0")
    Integer countVocabularyByTopicId(@Param("topic_id") Integer topic_id);

    // Update vocabulary entry by ID
    @Update("UPDATE vocabulary SET word = #{word}, definition = #{definition}, transcription = #{transcription}, " +
            "image = #{image}, last_modified = NOW() WHERE id = #{id} AND is_deleted = 0")
    void updateVocabulary(Vocabulary vocabulary);

    // Soft delete a vocabulary entry by setting is_deleted to 1
    @Update("UPDATE vocabulary SET is_deleted = 1, last_modified = NOW() WHERE id = #{id}")
    void softDeleteVocabulary(@Param("id") Integer id);

    // Permanently delete a vocabulary entry
    @Delete("DELETE FROM vocabulary WHERE id = #{id}")
    void deleteVocabularyPermanently(@Param("id") Integer id);
}
