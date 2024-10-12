package com.klearn.klearn_website.service.vocabulary;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.dto.dtoin.VocabularyDTOIn;
import com.klearn.klearn_website.mapper.VocabularyMapper;
import com.klearn.klearn_website.mapper.VocabularyTopicMapper;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.model.VocabularyTopic;

import lombok.AllArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;
@Service
@AllArgsConstructor
public class VocabularyService {
  
  private VocabularyMapper vocabularyMapper;
  private VocabularyTopicMapper vocabularyTopicMapper;

  public void createVocabulary(VocabularyDTOIn vocabularyDTOIn) {
    Vocabulary vocabulary = new Vocabulary();

    vocabulary.setWord(vocabularyDTOIn.getWord());
    vocabulary.setDefinition(vocabularyDTOIn.getDefinition());
    vocabulary.setTranscription(vocabularyDTOIn.getTranscription());
    vocabulary.setImage(vocabularyDTOIn.getImage());
    vocabulary.setIs_deleted(false);
    vocabulary.setLast_modified(LocalDateTime.now());
    VocabularyTopic vocabularyTopic = vocabularyTopicMapper.findVocabularyTopicById(vocabularyDTOIn.getTopic_id());
    if (vocabularyTopic == null) {
      throw new RuntimeException("Topic not found with ID: " + vocabularyDTOIn.getTopic_id());
    }

    vocabulary.setVocabularyTopic(vocabularyTopic);

    vocabularyMapper.createVocabulary(vocabulary);
  }

  public List<Vocabulary> getVocabularyByTopicId(Integer topic_id) {
    return vocabularyMapper.getVocabularyByTopicId(topic_id);
  }

  public Integer countVocabularyByTopicId(Integer topic_id) {
    return vocabularyMapper.countVocabularyByTopicId(topic_id);
  }
}
