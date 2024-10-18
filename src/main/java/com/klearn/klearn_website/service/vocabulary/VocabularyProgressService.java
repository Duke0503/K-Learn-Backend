package com.klearn.klearn_website.service.vocabulary;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.mapper.VocabularyProgressMapper;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.VocabularyProgress;
import com.klearn.klearn_website.model.VocabularyProgress.VocabularyProgressId;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.model.VocabularyTopic;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VocabularyProgressService {
    private final UserService userService;
    private final VocabularyTopicService vocabularyTopicService;
    private final VocabularyService vocabularyService;
    private final VocabularyProgressMapper vocabularyProgressMapper;

    /**
     * Create a new VocabularyProgress entry.
     *
     * @param vocabularyProgress the vocabulary progress to insert.
     */
    public void createVocabularyProgress(VocabularyProgress vocabularyProgress) {
        vocabularyProgressMapper.insertVocabularyProgress(vocabularyProgress);
    }

    /**
     * Get all VocabularyProgress entries by user ID and topic ID.
     *
     * @param userId  the user ID.
     * @param topicId the topic ID.
     * @return a list of VocabularyProgress entries.
     */
    public List<VocabularyProgress> getVocabularyProgressByUserIdAndTopicId(Integer userId, Integer topicId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        VocabularyTopic topic = vocabularyTopicService.getVocabularyTopicById(topicId).orElseThrow(
                () -> new IllegalArgumentException("Topic not found with ID: " + topicId));

        List<Vocabulary> listVocabulary = vocabularyService.getVocabularyByTopicId(topicId);

        // Insert a new progress entry for each vocabulary if it does not exist
        for (Vocabulary vocabulary : listVocabulary) {
            if (!vocabularyProgressMapper.existsByUserIdAndTopicId(userId, topicId, vocabulary.getId())) {
                VocabularyProgress newProgress = new VocabularyProgress(
                        new VocabularyProgressId(userId, vocabulary.getId(), topicId),
                        false,
                        LocalDateTime.now(),
                        false,
                        user,
                        vocabulary,
                        topic);
                vocabularyProgressMapper.insertVocabularyProgress(newProgress);
            }
        }

        return vocabularyProgressMapper.getVocabularyProgressByUserIdAndTopicId(userId, topicId);
    }

    /**
     * Find a VocabularyProgress entry by ID.
     *
     * @param userId       the user ID.
     * @param vocabularyId the vocabulary ID.
     * @param topicId      the topic ID.
     * @return VocabularyProgress entry.
     */
    public VocabularyProgress findVocabularyProgressById(Integer userId, Integer vocabularyId, Integer topicId) {
        return Optional.ofNullable(vocabularyProgressMapper.findVocabularyProgressById(userId, vocabularyId, topicId))
                .orElseThrow(() -> new IllegalArgumentException("VocabularyProgress not found with ID: " + userId + ", " + vocabularyId + ", " + topicId));
    }

    /**
     * Update an existing VocabularyProgress entry.
     *
     * @param vocabularyProgress the vocabulary progress to update.
     */
    public void updateVocabularyProgress(VocabularyProgress vocabularyProgress) {
        vocabularyProgressMapper.updateVocabularyProgress(vocabularyProgress);
    }

    /**
     * Mark a VocabularyProgress entry as learned.
     *
     * @param userId       the user ID.
     * @param topicId      the topic ID.
     * @param vocabularyId the vocabulary ID.
     */
    public void markVocabularyAsLearned(Integer userId, Integer topicId, Integer vocabularyId) {
        VocabularyProgress existingProgress = findVocabularyProgressById(userId, vocabularyId, topicId);
        vocabularyProgressMapper.markVocabularyAsLearned(userId, topicId, vocabularyId);
    }

    /**
     * Count the number of vocabularies not learned by user and topic ID.
     *
     * @param userId  the user ID.
     * @param topicId the topic ID.
     * @return count of not learned vocabularies.
     */
    public Integer countVocabularyNotLearned(Integer userId, Integer topicId) {
        return vocabularyProgressMapper.countVocabularyNotLearned(userId, topicId);
    }

    /**
     * Count the number of vocabularies learned by user and topic ID.
     *
     * @param userId  the user ID.
     * @param topicId the topic ID.
     * @return count of learned vocabularies.
     */
    public Integer countVocabularyLearned(Integer userId, Integer topicId) {
        return vocabularyProgressMapper.countVocabularyLearned(userId, topicId);
    }

    /**
     * Soft delete a VocabularyProgress entry.
     *
     * @param userId       the user ID.
     * @param vocabularyId the vocabulary ID.
     * @param topicId      the topic ID.
     */
    public void softDeleteVocabularyProgress(Integer userId, Integer vocabularyId, Integer topicId) {
        VocabularyProgress existingProgress = findVocabularyProgressById(userId, vocabularyId, topicId);
        vocabularyProgressMapper.softDeleteVocabularyProgress(userId, vocabularyId, topicId);
    }

    /**
     * Permanently delete a VocabularyProgress entry.
     *
     * @param userId       the user ID.
     * @param vocabularyId the vocabulary ID.
     * @param topicId      the topic ID.
     */
    public void deleteVocabularyProgressPermanently(Integer userId, Integer vocabularyId, Integer topicId) {
        VocabularyProgress existingProgress = findVocabularyProgressById(userId, vocabularyId, topicId);
        vocabularyProgressMapper.deleteVocabularyProgressPermanently(userId, vocabularyId, topicId);
    }
}
