package com.klearn.klearn_website.service.vocabulary;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.dto.vocabulary.CreateVocabularyDto;
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

  public void createVocabulary(CreateVocabularyDto createVocabularyDto) {
    Vocabulary vocabulary = new Vocabulary();

    vocabulary.setWord(createVocabularyDto.getWord());
    vocabulary.setDefinition(createVocabularyDto.getDefinition());
    vocabulary.setTranscription(createVocabularyDto.getTranscription());
    vocabulary.setImage(createVocabularyDto.getImage());
    vocabulary.setIs_deleted(false);
    vocabulary.setLast_modified(LocalDateTime.now());
    VocabularyTopic vocabularyTopic = vocabularyTopicMapper.findVocabularyTopicById(createVocabularyDto.getTopic_id());
    if (vocabularyTopic == null) {
      throw new RuntimeException("Topic not found with ID: " + createVocabularyDto.getTopic_id());
    }

    vocabulary.setVocabularyTopic(vocabularyTopic);

    vocabularyMapper.createVocabulary(vocabulary);
  }

  public List<Vocabulary> getVocabularyByTopicId(Integer topic_id) {
    return vocabularyMapper.getVocabularyByTopicId(topic_id);
  }
}
