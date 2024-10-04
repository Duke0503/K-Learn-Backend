package com.klearn.klearn_website.service.vocabulary;

import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.dto.vocabulary.CreateVocabularyTopicDto;
import com.klearn.klearn_website.mapper.CourseMapper;
import com.klearn.klearn_website.mapper.VocabularyTopicMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VocabularyTopicService {
  
  private VocabularyTopicMapper vocabularyTopicMapper;
  private CourseMapper courseMapper;

  public void createVocabularyTopic(CreateVocabularyTopicDto createVocabularyTopicDto) {
    VocabularyTopic vocabularyTopic = new VocabularyTopic();

    vocabularyTopic.setTopic_name(createVocabularyTopicDto.getTopic_name());
    vocabularyTopic.setTopic_description(createVocabularyTopicDto.getTopic_description());
    vocabularyTopic.setTopic_image(createVocabularyTopicDto.getTopic_image());
    vocabularyTopic.setCreated_at(LocalDateTime.now());
    vocabularyTopic.setLast_modified(LocalDateTime.now());
    vocabularyTopic.setIs_deleted(false);
    Course course = courseMapper.findCourseById(createVocabularyTopicDto.getCourse_id());
    if (course == null) {
      throw new RuntimeException("Course not found with ID: " + createVocabularyTopicDto.getCourse_id());
    }
    
    vocabularyTopic.setCourse(course);
    
    vocabularyTopicMapper.insertVocabularyTopic(vocabularyTopic);
  }

  public List<VocabularyTopic> getAllVocabularyTopic() {
    return vocabularyTopicMapper.getAll();
  }

  public List<VocabularyTopic> getVocabularyTopicById(Integer course_id) {
    return vocabularyTopicMapper.getAllByCourseId(course_id);
  }
}