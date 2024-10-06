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
  
  
}
