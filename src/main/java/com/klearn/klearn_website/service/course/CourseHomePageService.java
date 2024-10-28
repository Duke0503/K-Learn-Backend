package com.klearn.klearn_website.service.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.model.MyCourse;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.service.grammar.GrammarProgressService;
import com.klearn.klearn_website.service.grammar.GrammarService;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.service.vocabulary.VocabularyProgressService;
import com.klearn.klearn_website.service.vocabulary.VocabularyTopicService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseHomePageService {

    private final CourseService courseService;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final VocabularyTopicService vocabularyTopicService;
    private final GrammarService grammarService;
    private final MyCourseService myCourseService;
    private final VocabularyProgressService vocabularyProgressService;
    private final GrammarProgressService grammarProgressService;

    public String getTopicSection() {
        try {

            List<Course> listCourses = courseService.getAllCourses();

            Map<String, Object> responseData = new HashMap<>();

            // Get all vocabulary topics and grammar in one call per course
            List<VocabularyTopic> allTopics = new ArrayList<>();
            List<Grammar> allGrammars = new ArrayList<>();

            for (Course course : listCourses) {
                allTopics.addAll(vocabularyTopicService.getVocabularyTopicsByCourseId(course.getId()));
                allGrammars.addAll(grammarService.getGrammarByCourseId(course.getId()));
            }

            // Populate Topic Section
            List<Map<String, Object>> vocabTopicList = prepareVocabularyTopicData(allTopics);
            List<Map<String, Object>> grammarList = prepareGrammarData(allGrammars);

            responseData.put("topic_vocab", vocabTopicList);
            responseData.put("grammar", grammarList);

            return objectMapper.writeValueAsString(responseData);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching data for the main page", e);
        }
    }

    /**
     * Helper method to prepare vocabulary topic data.
     */
    private List<Map<String, Object>> prepareVocabularyTopicData(List<VocabularyTopic> topics) {
        List<Map<String, Object>> listTopicVocab = new ArrayList<>();
        for (VocabularyTopic topic : topics) {
            Map<String, Object> topicData = new HashMap<>();
            topicData.put("topic_id", topic.getId());
            topicData.put("course_id", topic.getCourse().getId());
            topicData.put("topic_name", topic.getTopic_name());
            topicData.put("course_name", topic.getCourse().getCourse_name());
            topicData.put("topic_image", topic.getTopic_image());
            listTopicVocab.add(topicData);
        }
        return listTopicVocab;
    }

    /**
     * Helper method to prepare grammar data.
     */
    private List<Map<String, Object>> prepareGrammarData(List<Grammar> grammars) {
        List<Map<String, Object>> listGrammar = new ArrayList<>();
        for (Grammar grammar : grammars) {
            Map<String, Object> grammarData = new HashMap<>();
            grammarData.put("grammar_id", grammar.getId());
            grammarData.put("course_id", grammar.getCourse().getId());
            grammarData.put("grammar_name", grammar.getGrammar_name());
            grammarData.put("course_name", grammar.getCourse().getCourse_name());
            grammarData.put("grammar_lesson_number", grammar.getLesson_number());
            listGrammar.add(grammarData);
        }
        return listGrammar;
    }

    public String getProgressSection(Integer userId) {
        // Validate that the user exists
        try {
            Map<String, Object> responseData = new HashMap<>();

            Optional<User> user = userService.getUserById(userId);
            if (!user.isEmpty()) {
                List<MyCourse> listMyCourses = myCourseService.getAllCourseByUserId(userId);
                // Populate Progress Section
                if (!listMyCourses.isEmpty()) {
                    List<Map<String, Object>> vocabProgressList = new ArrayList<>();
                    List<Map<String, Object>> grammarProgressList = new ArrayList<>();

                    for (MyCourse myCourse : listMyCourses) {
                        Integer courseId = myCourse.getCourse().getId();

                        // Calculate Vocabulary Progress for each topic in the course
                        List<VocabularyTopic> vocabTopics = vocabularyTopicService
                                .getVocabularyTopicsByCourseId(courseId);
                        for (VocabularyTopic topic : vocabTopics) {
                            int vocabularyProgress = calculateProgress(
                                    vocabularyProgressService.countVocabularyLearned(userId, topic.getId()),
                                    vocabularyProgressService.countVocabularyNotLearned(userId, topic.getId()));

                            if (vocabularyProgress > 0 && vocabularyProgress < 100) {
                                Map<String, Object> vocabProgressData = new HashMap<>();
                                vocabProgressData.put("topic_id", topic.getId());
                                vocabProgressData.put("topic_name", topic.getTopic_name());
                                vocabProgressData.put("course_id", topic.getCourse().getId());
                                vocabProgressData.put("course_name", topic.getCourse().getCourse_name());
                                vocabProgressData.put("topic_image", topic.getTopic_image());
                                vocabProgressData.put("vocabulary_progress", vocabularyProgress);
                                vocabProgressList.add(vocabProgressData);
                            }
                        }

                        // Calculate Grammar Progress for the course
                        int grammarProgress = calculateProgress(
                                grammarProgressService.countLearnedGrammar(userId, courseId),
                                grammarProgressService.countNotLearnedGrammar(userId, courseId));

                        if (grammarProgress > 0 && grammarProgress < 100) {
                            Map<String, Object> grammarProgressData = new HashMap<>();
                            grammarProgressData.put("course_id", courseId);
                            grammarProgressData.put("course_name", myCourse.getCourse().getCourse_name());
                            grammarProgressData.put("grammar_progress", grammarProgress);
                            grammarProgressList.add(grammarProgressData);
                        }
                    }

                    // Add vocab and grammar progress to progressSection
                    responseData.put("vocab", vocabProgressList);
                    responseData.put("grammar", grammarProgressList);

                }
            }

            return objectMapper.writeValueAsString(responseData);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching data for the main page", e);
        }
    }

    /**
     * Helper method to calculate progress percentage.
     */
    private int calculateProgress(int learned, int notLearned) {
        int total = learned + notLearned;
        return total == 0 ? 0 : (int) Math.ceil((double) learned * 100 / total);
    }

}
