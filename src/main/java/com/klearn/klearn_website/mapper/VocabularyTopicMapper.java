package com.klearn.klearn_website.mapper;

import com.klearn.klearn_website.model.VocabularyTopic;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface VocabularyTopicMapper {

    // Create a new vocabulary topic
    @Insert("INSERT INTO vocabulary_topic (course_id, topic_name, topic_description, topic_image, created_at, last_modified, is_deleted) "
            +
            "VALUES (#{course.id}, #{topic_name}, #{topic_description}, #{topic_image}, #{created_at}, #{last_modified}, #{is_deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertVocabularyTopic(VocabularyTopic vocabularyTopic);

    // Get all vocabulary topics that are not deleted
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

    // Get all vocabulary topics by course ID that are not deleted
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
    List<VocabularyTopic> getAllByCourseId(@Param("course_id") Integer course_id);

    // Find a vocabulary topic by its ID that is not deleted
    @Select("SELECT * FROM vocabulary_topic WHERE id = #{topic_id} AND is_deleted = 0")
    VocabularyTopic findVocabularyTopicById(@Param("topic_id") Integer topic_id);

    // Get course ID by vocabulary topic ID
    @Select("SELECT course_id FROM vocabulary_topic WHERE id = #{topic_id} AND is_deleted = 0")
    Integer getCourseIdByTopicId(@Param("topic_id") Integer topic_id);

    // Update a vocabulary topic
    @Update("UPDATE vocabulary_topic SET topic_name = #{topic_name}, topic_description = #{topic_description}, topic_image = #{topic_image}, "
            +
            "last_modified = NOW() WHERE id = #{id} AND is_deleted = 0")
    void updateVocabularyTopic(VocabularyTopic vocabularyTopic);

    // Soft delete a vocabulary topic by setting is_deleted to 1
    @Update("UPDATE vocabulary_topic SET is_deleted = 1, last_modified = NOW() WHERE id = #{topic_id}")
    void softDeleteVocabularyTopic(@Param("topic_id") Integer topic_id);

    // Permanently delete a vocabulary topic (use with caution)
    @Delete("DELETE FROM vocabulary_topic WHERE id = #{topic_id}")
    void deleteVocabularyTopicPermanently(@Param("topic_id") Integer topic_id);
}
