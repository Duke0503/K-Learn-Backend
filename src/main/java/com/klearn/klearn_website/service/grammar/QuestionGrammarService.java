package com.klearn.klearn_website.service.grammar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klearn.klearn_website.dto.dtoin.QuestionGrammarDTOIn;
import com.klearn.klearn_website.mapper.GrammarMapper;
import com.klearn.klearn_website.mapper.QuestionGrammarMapper;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.model.QuestionGrammar;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionGrammarService {

    private final QuestionGrammarMapper questionGrammarMapper;
    private final GrammarMapper grammarMapper;
    private final ObjectMapper objectMapper;

    public void createQuestionGrammar(QuestionGrammarDTOIn questionGrammarDTOIn) {
        String incorrectAnswersAsString = convertListToString(questionGrammarDTOIn.getIncorrect_answer());

        Grammar grammar = grammarMapper.getGrammarById(questionGrammarDTOIn.getGrammar_id());
        if (grammar == null) {
            throw new RuntimeException("Grammar not found with ID: " + questionGrammarDTOIn.getGrammar_id());
        }

        QuestionGrammar questionGrammar = new QuestionGrammar();
        questionGrammar.setQuestion_text(questionGrammarDTOIn.getQuestion_text());
        questionGrammar.setCorrect_answer(questionGrammarDTOIn.getCorrect_answer());
        questionGrammar.setIncorrect_answer(incorrectAnswersAsString);
        questionGrammar.setQuiz_type(questionGrammarDTOIn.getQuiz_type());
        questionGrammar.setGrammar(grammar);
        questionGrammar.setCreated_at(LocalDateTime.now());
        questionGrammar.setLast_modified(LocalDateTime.now());
        questionGrammar.setIs_deleted(false);

        questionGrammarMapper.insertQuestionGrammar(questionGrammar);
    }

    private String convertListToString(List<String> incorrectAnswers) {
        try {
            return objectMapper.writeValueAsString(incorrectAnswers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting incorrect answers to string", e);
        }
    }

    public List<QuestionGrammar> getAllQuestionsByGrammarId(Integer grammarId) {
        return questionGrammarMapper.getQuestionsByGrammarId(grammarId);
    }
}
