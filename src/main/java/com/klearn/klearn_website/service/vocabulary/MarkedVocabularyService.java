package com.klearn.klearn_website.service.vocabulary;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.dto.dtoout.VocabularyQuestionDTOOut;
import com.klearn.klearn_website.mapper.MarkedVocabularyMapper;
import com.klearn.klearn_website.model.MarkedVocabulary;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MarkedVocabularyService {
    private final MarkedVocabularyMapper markedVocabularyMapper;
    private final UserService userService;
    private final VocabularyService vocabularyService;
    private final Random random = new Random();

    /**
     * Create a new MarkedVocabulary entry.
     *
     * @param markedVocabularyMapper the marked vocabulary to insert.
     */
    public void createMarkedVocab(MarkedVocabulary markedVocabulary) {
        markedVocabularyMapper.insertMarkedVocabulary(markedVocabulary);
    }

    /**
     * Create a new MarkedVocabulary by userId and vocabularyId
     * 
     * @param userId 
     * @param vocabularyId 
     */
    public void createMarkedVocabByUserAndVocabulary(Integer userId, Integer vocabularyId) {
        User user = userService.getUserById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Vocabulary vocabulary = vocabularyService.getVocabularyById(vocabularyId);

        Optional<MarkedVocabulary> markedVocabulary = markedVocabularyMapper.findAllByUserIdAndVocabId(userId, vocabularyId);

        if (markedVocabulary.isEmpty()) {
            MarkedVocabulary newMarkedVocabulary = new MarkedVocabulary();
            newMarkedVocabulary.setIs_deleted(false);
            newMarkedVocabulary.setLast_modified(LocalDateTime.now());
            newMarkedVocabulary.setUser(user);
            newMarkedVocabulary.setVocabulary(vocabulary);

            markedVocabularyMapper.insertMarkedVocabulary(newMarkedVocabulary);
        } else {
            markedVocabularyMapper.updateLastModified(vocabularyId, LocalDateTime.now());
        }
    }

    /**
     * Get a list Marked Vocabulary by userId
     * 
     * @param userId 
     * @return List Vocabulary
     */
    public List<MarkedVocabulary> getListMarkedVocabularybyUserId(Integer userId) {
        return markedVocabularyMapper.findAllByUserId(userId);
    }

    /**
     * Soft Delete Marked Vocabulary
     * 
     * @param vocabularyId
     */
    public void softDelete(Integer vocabularyId) {
        markedVocabularyMapper.deleteByVocabularyId(vocabularyId);
    }

    public MarkedVocabulary findAllByUserIdAndVocabId(Integer userId, Integer vocabId) {
        Optional<MarkedVocabulary> markedVocab = markedVocabularyMapper.findAllByUserIdAndVocabId(userId, vocabId);
        return markedVocab.orElse(null); // Unwrap the Optional safely
    }

    public Boolean existsMarkedVocab(Integer userId, Integer vocabId) {
        Optional<MarkedVocabulary> markedVocab = markedVocabularyMapper.findAllByUserIdAndVocabId(userId, vocabId);
        if (markedVocab.isEmpty()) {
            return false;
        } else return true;
        
    }

    public List<VocabularyQuestionDTOOut> generateQuizFromVocabularyIds(List<Integer> vocabularyIds) {
        // Initialize the list to hold vocabulary items
        List<Vocabulary> vocabularyList = new ArrayList<>();
        
        // Retrieve each vocabulary item by ID and add it to the list
        for (Integer id : vocabularyIds) {
            Vocabulary vocabulary = vocabularyService.getVocabularyById(id);
            if (vocabulary != null) { // Ensure the vocabulary exists before adding
                vocabularyList.add(vocabulary);
            }
        }
        
        if (vocabularyList.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Generate quiz questions from the specified vocabulary list
        List<VocabularyQuestionDTOOut> quizQuestions = new ArrayList<>();
        boolean onlyEssayQuestions = vocabularyList.size() < 4;
    
        for (Vocabulary vocabulary : vocabularyList) {
            String questionType = onlyEssayQuestions ? "essay" : (random.nextBoolean() ? "multichoice" : "essay");
            quizQuestions.add(createVocabularyQuestion(vocabulary, questionType, getAllVocabularyDefinitions()));
        }
    
        return quizQuestions;
    }
    
    /**
     * Creates a VocabularyQuestionDTOOut for a given vocabulary.
     *
     * @param vocabulary   The vocabulary.
     * @param questionType The type of the question ("multichoice" or "essay").
     * @param allDefinitions The list of all vocabulary definitions to generate options.
     * @return A VocabularyQuestionDTOOut.
     */
    private VocabularyQuestionDTOOut createVocabularyQuestion(Vocabulary vocabulary, String questionType, List<String> allDefinitions) {
        List<String> options = questionType.equals("multichoice") ? generateOptions(vocabulary.getDefinition(), allDefinitions) : new ArrayList<>();
    
        return new VocabularyQuestionDTOOut(
                vocabulary.getId(),
                questionType,
                vocabulary.getWord(),
                vocabulary.getDefinition(),
                options
        );
    }
    
    /**
     * Generates multiple-choice options for a vocabulary question.
     *
     * @param correctDefinition The correct definition for the vocabulary.
     * @param allDefinitions    The list of all definitions from which distractors can be chosen.
     * @return A list of options including the correct answer.
     */
    private List<String> generateOptions(String correctDefinition, List<String> allDefinitions) {
        List<String> options = new ArrayList<>();
        options.add(correctDefinition);
    
        List<String> distractors = new ArrayList<>(allDefinitions);
        distractors.remove(correctDefinition);
    
        // Shuffle and pick 3 distractors
        Collections.shuffle(distractors);
        for (int i = 0; i < 3 && i < distractors.size(); i++) {
            options.add(distractors.get(i));
        }
    
        Collections.shuffle(options);
        return options;
    }
    
    /**
     * Retrieves all vocabulary definitions to generate multiple-choice options.
     *
     * @return List of definitions for all vocabulary.
     */
    private List<String> getAllVocabularyDefinitions() {
        List<Vocabulary> allVocabulary = vocabularyService.getAllVocabulary();
        List<String> definitions = new ArrayList<>();
        for (Vocabulary vocab : allVocabulary) {
            definitions.add(vocab.getDefinition());
        }
        return definitions;
    }
    
}
