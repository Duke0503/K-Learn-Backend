package com.klearn.klearn_website.service.vocabulary;

import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

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
        markedVocabularyMapper.deleteById(vocabularyId);
    }

    public MarkedVocabulary findAllByUserIdAndVocabId(Integer userId, Integer vocabId) {
        Optional<MarkedVocabulary> markedVocab = markedVocabularyMapper.findAllByUserIdAndVocabId(userId, vocabId);
        return markedVocab.orElse(null); // Unwrap the Optional safely
    }
}
