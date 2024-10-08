package com.klearn.klearn_website.service.vocabulary;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.mapper.VocabularyMapper;
import com.klearn.klearn_website.mapper.VocabularyProgressMapper;
import com.klearn.klearn_website.mapper.VocabularyTopicMapper;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.model.VocabularyProgress;
import com.klearn.klearn_website.model.VocabularyProgress.VocabularyProgressId;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VocabularyProgressService {
  private UserMapper userMapper;
  private VocabularyTopicMapper vocabularyTopicMapper;
  private VocabularyMapper vocabularyMapper;
  private VocabularyProgressMapper vocabularyProgressMapper;

  public List<VocabularyProgress> getVocabularyByUserIdAndTopicId(Integer userId, Integer topicId) {
    User user = userMapper.findUserById(userId);
    VocabularyTopic topic = vocabularyTopicMapper.findVocabularyTopicById(topicId);

    List<Vocabulary> listVocabulary = vocabularyMapper.getVocabularyByTopicId(topicId);

    listVocabulary.forEach(vocabulary -> {

      if (!vocabularyProgressMapper.existsByUserIdAndTopicId(userId, topicId, vocabulary.getId())) {

        VocabularyProgress newProgress = new VocabularyProgress(
          new VocabularyProgressId(userId, vocabulary.getId(), topicId),
          false,
          LocalDateTime.now(),
          false,
          user,
          vocabulary,
          topic
        );
        vocabularyProgressMapper.insertVocabularyProgress(newProgress);
      }
    });

    return vocabularyProgressMapper.getVocabularyProgressByUserIdAndTopicId(userId, topicId);
  }
}
