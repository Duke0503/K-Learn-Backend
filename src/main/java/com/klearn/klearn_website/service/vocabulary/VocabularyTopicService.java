package com.klearn.klearn_website.service.vocabulary;

import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.dto.dtoin.VocabularyTopicDTOIn;
import com.klearn.klearn_website.service.course.CourseService;
import com.klearn.klearn_website.mapper.VocabularyTopicMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VocabularyTopicService {

    private final VocabularyTopicMapper vocabularyTopicMapper;
    private final CourseService courseService;

    /**
     * Create a new VocabularyTopic using the provided DTO.
     *
     * @param vocabularyTopicDTOIn the DTO containing vocabulary topic details.
     */
    public void createVocabularyTopic(VocabularyTopicDTOIn vocabularyTopicDTOIn) {
        // Build a new VocabularyTopic entity from the DTO
        VocabularyTopic vocabularyTopic = new VocabularyTopic();
        vocabularyTopic.setTopic_name(vocabularyTopicDTOIn.getTopic_name());
        vocabularyTopic.setTopic_description(vocabularyTopicDTOIn.getTopic_description());
        vocabularyTopic.setTopic_image(vocabularyTopicDTOIn.getTopic_image());
        vocabularyTopic.setCreated_at(LocalDateTime.now());
        vocabularyTopic.setLast_modified(LocalDateTime.now());
        vocabularyTopic.setIs_deleted(false);

        // Fetch the associated course using CourseService
        Optional<Course> courseOptional = courseService.getCourseById(vocabularyTopicDTOIn.getCourse_id());
        if (courseOptional.isEmpty()) {
            throw new IllegalArgumentException("Course not found with ID: " + vocabularyTopicDTOIn.getCourse_id());
        }

        vocabularyTopic.setCourse(courseOptional.get());

        // Insert the new vocabulary topic into the database
        vocabularyTopicMapper.insertVocabularyTopic(vocabularyTopic);
    }

    /**
     * Get all VocabularyTopic entities that are not deleted.
     *
     * @return a list of VocabularyTopic objects.
     */
    public List<VocabularyTopic> getAllVocabularyTopics() {
        return vocabularyTopicMapper.getAll();
    }

    /**
     * Get all VocabularyTopic entities by a specific course ID that are not deleted.
     *
     * @param courseId the ID of the course.
     * @return a list of VocabularyTopic objects related to the specified course.
     */
    public List<VocabularyTopic> getVocabularyTopicsByCourseId(Integer courseId) {
        return vocabularyTopicMapper.getAllByCourseId(courseId);
    }

    /**
     * Get a VocabularyTopic entity by its ID.
     *
     * @param topicId the ID of the vocabulary topic to retrieve.
     * @return the VocabularyTopic object if found, or null if not.
     */
    public Optional<VocabularyTopic> getVocabularyTopicById(Integer topicId) {
        return Optional.ofNullable(vocabularyTopicMapper.findVocabularyTopicById(topicId));
    }
    /**
     * Update an existing VocabularyTopic by ID.
     *
     * @param topicId              the ID of the vocabulary topic to update.
     * @param vocabularyTopicDTOIn the DTO containing updated information.
     */
    public void updateVocabularyTopic(Integer topicId, VocabularyTopicDTOIn vocabularyTopicDTOIn) {
        VocabularyTopic existingTopic = vocabularyTopicMapper.findVocabularyTopicById(topicId);
        if (existingTopic == null) {
            throw new IllegalArgumentException("VocabularyTopic not found with ID: " + topicId);
        }

        existingTopic.setTopic_name(vocabularyTopicDTOIn.getTopic_name());
        existingTopic.setTopic_description(vocabularyTopicDTOIn.getTopic_description());
        existingTopic.setTopic_image(vocabularyTopicDTOIn.getTopic_image());
        existingTopic.setLast_modified(LocalDateTime.now());

        vocabularyTopicMapper.updateVocabularyTopic(existingTopic);
    }

    /**
     * Soft delete a VocabularyTopic by ID.
     *
     * @param topicId the ID of the vocabulary topic to be deleted.
     */
    public void softDeleteVocabularyTopic(Integer topicId) {
        VocabularyTopic existingTopic = vocabularyTopicMapper.findVocabularyTopicById(topicId);
        if (existingTopic == null) {
            throw new IllegalArgumentException("VocabularyTopic not found with ID: " + topicId);
        }

        vocabularyTopicMapper.softDeleteVocabularyTopic(topicId);
    }

}
