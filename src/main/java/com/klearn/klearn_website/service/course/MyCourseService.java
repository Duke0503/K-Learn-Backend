package com.klearn.klearn_website.service.course;

import com.klearn.klearn_website.mapper.MyCourseMapper;
import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.mapper.VocabularyProgressMapper;
import com.klearn.klearn_website.mapper.VocabularyTopicMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klearn.klearn_website.dto.dtoin.MyCourseDTOIn;
import com.klearn.klearn_website.mapper.CourseMapper;
import com.klearn.klearn_website.model.MyCourse;
import com.klearn.klearn_website.model.MyCourse.MyCourseId;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.model.Course;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class MyCourseService {
  private MyCourseMapper myCourseMapper;
  private UserMapper userMapper;
  private CourseMapper courseMapper;
  private VocabularyTopicMapper vocabularyTopicMapper;
  private VocabularyProgressMapper vocabularyProgressMapper;

  public void insertMyCourse(MyCourseDTOIn myCourseDTOIn) {
    User user = userMapper.findUserById(myCourseDTOIn.getUser_id());
    if (user == null) {
      throw new RuntimeException("User not found with ID: " + myCourseDTOIn.getCourse_id());
    }
    Course course = courseMapper.findCourseById(myCourseDTOIn.getCourse_id());
    if (course == null) {
      throw new RuntimeException("Course not found with ID: " + myCourseDTOIn.getCourse_id());
    }

    MyCourse mycourse = new MyCourse(
      new MyCourseId(user.getId(), course.getId()),
      LocalDateTime.now(),
      "pending",
      LocalDateTime.now(),
      0,
      false,
      user,
      course
    );

    myCourseMapper.insertMyCourse(mycourse);
  }

  public List<MyCourse> getMyCourseByUserId(Integer userId) {
    return myCourseMapper.getMyCourseByUserId(userId);
  }

  public String getVocabularyProgressByUserIdAndCourseId(Integer userId, Integer courseId) {
    try {
      MyCourse myCourse = myCourseMapper.getMyCourseByUserIdAndCourseId(userId, courseId);
      if (myCourse == null) {
        return "{}"; 
      }

        List<VocabularyTopic> topics = vocabularyTopicMapper.getAllByCourseId(courseId);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("course_id", myCourse.getCourse().getId());
        responseData.put("course_name", myCourse.getCourse().getCourse_name());
        responseData.put("course_image", myCourse.getCourse().getCourse_image());
        responseData.put("course_description", myCourse.getCourse().getCourse_description());
        responseData.put("course_level", myCourse.getCourse().getCourse_level());  

        int learnedTopics = 0;
        int totalTopics = 0;

        for (VocabularyTopic topic : topics) {
            int learnedWords = vocabularyProgressMapper.countVocabularyLearned(userId, topic.getId());
            int notLearnedWords = vocabularyProgressMapper.countVocabularyNotLearned(userId, topic.getId());

            int topic_progress;
            int totalWords = learnedWords + notLearnedWords;

            if (totalWords == 0) {
                topic_progress = 0; 
            } else {
                double progress = (double) learnedWords * 100 / totalWords; 
                topic_progress = (int) Math.ceil(progress);  
            }

            if (topic_progress >= 80) learnedTopics++;
            totalTopics++;

            Map<String, Object> topicProgress = new HashMap<>();
            topicProgress.put("topic_id", topic.getId());
            topicProgress.put("topic_name", topic.getTopic_name());
            topicProgress.put("topic_image", topic.getTopic_image());
            topicProgress.put("topic_description", topic.getTopic_description());
            topicProgress.put("learned_words", learnedWords);
            topicProgress.put("total_words", totalWords);
            topicProgress.put("topic_progress", topic_progress);

            responseData.put("topic_" + topic.getId(), topicProgress);
        }

        responseData.put("learned_topic", learnedTopics);
        responseData.put("total_topic", totalTopics);

        if (totalTopics == 0) {
            responseData.put("course_progress", 0);
        } else {
            double courseProgress = (double) learnedTopics * 100 / totalTopics;
            responseData.put("course_progress", (int) Math.ceil(courseProgress));
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseData);

    } catch (Exception e) {
        System.err.println("Error occurred while fetching course progress: " + e.getMessage());
        throw new RuntimeException("Error fetching progress", e);
    }
  }
}