package com.klearn.klearn_website.mapper;

import org.apache.ibatis.annotations.*;
import com.klearn.klearn_website.model.MarkedVocabulary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface MarkedVocabularyMapper {

    // Insert Marked Vocabulary
    @Insert("INSERT INTO marked_vocabulary(last_modified, is_deleted, user_id, vocabulary_id) " +
            "VALUES (#{last_modified}, #{is_deleted}, #{user.id}, #{vocabulary.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertMarkedVocabulary(MarkedVocabulary markedVocabulary);

    // Find by ID
    @Select("SELECT * FROM marked_vocabulary WHERE id = #{id} AND is_deleted = false")
    Optional<MarkedVocabulary> findById(Integer id);

    // Find all Marked Vocabularies for a User
    @Select("""
        SELECT mv.*, 
               u.id AS user_id, u.username, u.email, u.fullname,
               v.id AS vocabulary_id, v.word, v.definition, v.image, v.transcription
        FROM marked_vocabulary mv
        JOIN users u ON mv.user_id = u.id
        JOIN vocabulary v ON mv.vocabulary_id = v.id
        WHERE mv.user_id = #{userId} AND mv.is_deleted = false
    """)
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "is_deleted", column = "is_deleted"),
        @Result(property = "last_modified", column = "last_modified"),
    
        // Mapping User object properties
        @Result(property = "user.id", column = "user_id"),
        @Result(property = "user.username", column = "username"),
        @Result(property = "user.email", column = "email"),
        @Result(property = "user.fullname", column = "fullname"),
        
        // Mapping Vocabulary object properties
        @Result(property = "vocabulary.id", column = "vocabulary_id"),
        @Result(property = "vocabulary.word", column = "word"),
        @Result(property = "vocabulary.definition", column = "definition"),
        @Result(property = "vocabulary.transcription", column = "transcription"),
        @Result(property = "vocabulary.image", column = "image")
    })
    List<MarkedVocabulary> findAllByUserId(Integer userId);

    // Find Marked Vocabulary for a User
    @Select("SELECT * FROM marked_vocabulary WHERE user_id = #{userId} AND vocabulary_id = #{vocabulary_id} AND is_deleted = false")
    Optional<MarkedVocabulary> findAllByUserIdAndVocabId(Integer userId, Integer vocabulary_id);

    // Delete by ID (Soft Delete)
    @Update("UPDATE marked_vocabulary SET is_deleted = true WHERE vocabulary_id = #{vocabularyId}")
    void deleteByVocabularyId(@Param("vocabularyId") Integer vocabularyId);

    // Update last modified timestamp
    @Update("UPDATE marked_vocabulary SET last_modified = #{last_modified} WHERE id = #{id}")
    void updateLastModified(@Param("id") Integer id, @Param("last_modified") LocalDateTime lastModified);
}
