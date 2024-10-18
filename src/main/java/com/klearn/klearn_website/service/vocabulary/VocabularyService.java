package com.klearn.klearn_website.service.vocabulary;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.dto.dtoin.VocabularyDTOIn;
import com.klearn.klearn_website.mapper.VocabularyMapper;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.service.vocabulary.VocabularyTopicService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VocabularyService {

    private final VocabularyMapper vocabularyMapper;
    private final VocabularyTopicService vocabularyTopicService;

    /**
     * Creates a new Vocabulary entity using the provided DTO.
     *
     * @param vocabularyDTOIn the DTO containing vocabulary details.
     */
    public void createVocabulary(VocabularyDTOIn vocabularyDTOIn) {
        Vocabulary vocabulary = new Vocabulary();

        // Set vocabulary fields using DTO
        vocabulary.setWord(vocabularyDTOIn.getWord());
        vocabulary.setDefinition(vocabularyDTOIn.getDefinition());
        vocabulary.setTranscription(vocabularyDTOIn.getTranscription());
        vocabulary.setImage(vocabularyDTOIn.getImage());
        vocabulary.setIs_deleted(false);
        vocabulary.setLast_modified(LocalDateTime.now());

        Optional<VocabularyTopic> vocabularyTopic = vocabularyTopicService.getVocabularyTopicById(vocabularyDTOIn.getTopic_id());
        if (vocabularyTopic.isEmpty()) {
            throw new RuntimeException("Topic not found with ID: " + vocabularyDTOIn.getTopic_id());
        }

        vocabulary.setVocabularyTopic(vocabularyTopic.get());

        // Insert the vocabulary into the database
        vocabularyMapper.createVocabulary(vocabulary);
    }

    /**
     * Retrieves all Vocabulary entities associated with a specific topic ID.
     *
     * @param topicId the ID of the vocabulary topic.
     * @return a list of Vocabulary entities.
     */
    public List<Vocabulary> getVocabularyByTopicId(Integer topicId) {
        return vocabularyMapper.getVocabularyByTopicId(topicId);
    }

    /**
     * Counts the number of Vocabulary entities associated with a specific topic ID.
     *
     * @param topicId the ID of the vocabulary topic.
     * @return the count of Vocabulary entities.
     */
    public Integer countVocabularyByTopicId(Integer topicId) {
        return vocabularyMapper.countVocabularyByTopicId(topicId);
    }

    /**
     * Updates an existing Vocabulary entity.
     *
     * @param vocabularyId  the ID of the vocabulary to update.
     * @param vocabularyDTOIn the DTO containing updated information.
     */
    public void updateVocabulary(Integer vocabularyId, VocabularyDTOIn vocabularyDTOIn) {
        Vocabulary existingVocabulary = vocabularyMapper.getVocabularyById(vocabularyId);
        if (existingVocabulary == null) {
            throw new RuntimeException("Vocabulary not found with ID: " + vocabularyId);
        }

        // Update vocabulary fields using DTO
        existingVocabulary.setWord(vocabularyDTOIn.getWord());
        existingVocabulary.setDefinition(vocabularyDTOIn.getDefinition());
        existingVocabulary.setTranscription(vocabularyDTOIn.getTranscription());
        existingVocabulary.setImage(vocabularyDTOIn.getImage());
        existingVocabulary.setLast_modified(LocalDateTime.now());

        vocabularyMapper.updateVocabulary(existingVocabulary);
    }

    /**
     * Soft deletes a Vocabulary entity by marking it as deleted.
     *
     * @param vocabularyId the ID of the vocabulary to delete.
     */
    public void softDeleteVocabulary(Integer vocabularyId) {
        Vocabulary existingVocabulary = vocabularyMapper.getVocabularyById(vocabularyId);
        if (existingVocabulary == null) {
            throw new RuntimeException("Vocabulary not found with ID: " + vocabularyId);
        }

        vocabularyMapper.softDeleteVocabulary(vocabularyId);
    }

    /**
     * Permanently deletes a Vocabulary entity.
     *
     * @param vocabularyId the ID of the vocabulary to delete.
     */
    public void deleteVocabularyPermanently(Integer vocabularyId) {
        Vocabulary existingVocabulary = vocabularyMapper.getVocabularyById(vocabularyId);
        if (existingVocabulary == null) {
            throw new RuntimeException("Vocabulary not found with ID: " + vocabularyId);
        }

        vocabularyMapper.deleteVocabularyPermanently(vocabularyId);
    }
}
