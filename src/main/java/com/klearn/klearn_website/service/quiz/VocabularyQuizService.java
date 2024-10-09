package com.klearn.klearn_website.service.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.mapper.VocabularyProgressMapper;
import com.klearn.klearn_website.model.VocabularyProgress;
import com.klearn.klearn_website.model.VocabularyQuestion;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VocabularyQuizService {
  private VocabularyProgressMapper vocabularyProgressMapper;

  public List<VocabularyQuestion> createVocabularyQuiz(Integer userId, Integer topicId) {
    List<VocabularyProgress> listVocabulary = vocabularyProgressMapper.getVocabularyProgressByUserIdAndTopicId(userId, topicId);

    List<String> definitions = new ArrayList<>();
    
    listVocabulary.forEach(vocabularyProgress -> {
      definitions.add(vocabularyProgress.getVocabulary().getDefinition());
    });

    List<VocabularyQuestion> listQuestions = new ArrayList<>();

    listVocabulary.forEach(vocabularyProgress -> {
      if (vocabularyProgress.getIs_learned() == false) {
        Integer vocabularyId = vocabularyProgress.getVocabulary().getId();
        String word = vocabularyProgress.getVocabulary().getWord();
        String definition = vocabularyProgress.getVocabulary().getDefinition();

        List<String> options = new ArrayList<>();
        options.add(definition);

        while (options.size() < 4) {
          String randomDefinition = definitions.get((int) (Math.random() * definitions.size()));

          if (!options.contains(randomDefinition)) {
            options.add(randomDefinition);
          }
        }

        Collections.shuffle(options);

        listQuestions.add(new VocabularyQuestion(
          vocabularyId,
          "multichoice",
          word,
          definition,
          options
        ));

        listQuestions.add(new VocabularyQuestion(
          vocabularyId,
          "essay",
          word,
          definition,
          Collections.emptyList()
        ));
      }
    });

    return listQuestions;
  }
}
