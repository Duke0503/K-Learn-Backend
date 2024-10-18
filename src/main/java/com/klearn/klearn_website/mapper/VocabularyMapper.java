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

    @Select("SELECT v.*, vt.*, c.* FROM vocabulary v " +
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
            @Result(property = "vocabularyTopic.last_modified", column = "last_modified"),
            @Result(property = "vocabularyTopic.is_deleted", column = "is_deleted"),

            @Result(property = "vocabularyTopic.course.id", column = "course_id"),
            @Result(property = "vocabularyTopic.course.course_name", column = "course_name"),
            @Result(property = "vocabularyTopic.course.course_level", column = "course_level"),
            @Result(property = "vocabularyTopic.course.course_description", column = "course_description"),
            @Result(property = "vocabularyTopic.course.course_image", column = "course_image"),
            @Result(property = "vocabularyTopic.course.course_price", column = "course_price"),
            @Result(property = "vocabularyTopic.course.created_at", column = "created_at"),
            @Result(property = "vocabularyTopic.course.last_modified", column = "last_modified"),
            @Result(property = "vocabularyTopic.course.is_deleted", column = "is_deleted")
    })
    List<Vocabulary> getVocabularyByTopicId(@Param("topic_id") Integer topic_id);

    @Select("SELECT COUNT(*) FROM vocabulary WHERE topic_id = #{topic_id} AND is_deleted = 0")
    Integer countVocabularyByTopicId(@Param("topic_id") Integer topic_id);
}
