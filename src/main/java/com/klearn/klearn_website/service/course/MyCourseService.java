package com.klearn.klearn_website.service.course;

import com.klearn.klearn_website.mapper.MyCourseMapper;
import com.klearn.klearn_website.mapper.QuestionGrammarMapper;
import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.mapper.VocabularyProgressMapper;
import com.klearn.klearn_website.mapper.VocabularyTopicMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klearn.klearn_website.dto.dtoin.MyCourseDTOIn;
import com.klearn.klearn_website.mapper.CourseMapper;
import com.klearn.klearn_website.mapper.GrammarMapper;
import com.klearn.klearn_website.mapper.GrammarProgressMapper;
import com.klearn.klearn_website.model.MyCourse;
import com.klearn.klearn_website.model.QuestionGrammar;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.model.GrammarProgress;
import com.klearn.klearn_website.model.MyCourse.MyCourseId;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.model.Course;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class MyCourseService {
    private MyCourseMapper myCourseMapper;
    private UserMapper userMapper;
    private CourseMapper courseMapper;
    private VocabularyTopicMapper vocabularyTopicMapper;
    private VocabularyProgressMapper vocabularyProgressMapper;
    private GrammarMapper grammarMapper;
    private GrammarProgressMapper grammarProgressMapper;
    private QuestionGrammarMapper questionGrammarMapper;

    public void insertMyCourse(MyCourseDTOIn myCourseDTOIn) {
        // Check if user exists
        User user = userMapper.findUserById(myCourseDTOIn.getUser_id());
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + myCourseDTOIn.getUser_id());
        }
        // Check if course exists
        Course course = courseMapper.findCourseById(myCourseDTOIn.getCourse_id());
        if (course == null) {
            throw new RuntimeException("Course not found with ID: " + myCourseDTOIn.getCourse_id());
        }

        // Create new MyCourse instance
        MyCourse mycourse = new MyCourse(
                new MyCourseId(user.getId(), course.getId()),
                LocalDateTime.now(),
                "pending",
                LocalDateTime.now(),
                false,
                user,
                course);

        // Insert into the database
        myCourseMapper.insertMyCourse(mycourse);
    }

    public String getMyCourseByUserId(Integer userId) {
        try {
            List<MyCourse> listMyCourses = myCourseMapper.getMyCourseByUserId(userId);
            if (listMyCourses.isEmpty()) {
                return "{}";
            }

            Map<String, Object> responseData = new HashMap<>();

            for (MyCourse myCourse : listMyCourses) {
                Map<String, Object> course = new HashMap<>();
                course.put("id", myCourse.getCourse().getId());
                course.put("name", myCourse.getCourse().getCourse_name());

                // Calculate the progress of Grammar
                int learnedGrammar = grammarProgressMapper.countLearnedGrammar(userId, myCourse.getCourse().getId());
                int notLearnedGrammar = grammarProgressMapper.countNotLearnedGrammar(userId,
                        myCourse.getCourse().getId());
                int grammar_progress = (learnedGrammar + notLearnedGrammar) == 0 ? 0
                        : (int) Math.ceil((double) learnedGrammar * 100 / (learnedGrammar + notLearnedGrammar));

                // Calculate the progress of Vocabulary
                int vocab_progress = 0;
                List<VocabularyTopic> topics = vocabularyTopicMapper.getAllByCourseId(myCourse.getCourse().getId());
                if (topics.isEmpty()) {
                    vocab_progress = 0;
                } else {
                    int learnedTopics = 0;
                    int totalTopics = 0;
                    for (VocabularyTopic topic : topics) {
                        int learnedWords = vocabularyProgressMapper.countVocabularyLearned(userId, topic.getId());
                        int notLearnedWords = vocabularyProgressMapper.countVocabularyNotLearned(userId, topic.getId());

                        int totalWords = learnedWords + notLearnedWords;
                        int topic_progress = (totalWords == 0) ? 0
                                : (int) Math.ceil((double) learnedWords * 100 / totalWords);

                        if (topic_progress >= 80)
                            learnedTopics++;
                        totalTopics++;
                    }
                    vocab_progress = totalTopics == 0 ? 0 : (int) Math.ceil((double) learnedTopics * 100 / totalTopics);
                }

                course.put("progress", (int) Math.ceil(((double) grammar_progress) + (double) vocab_progress) / 2);

                responseData.put("course_" + myCourse.getCourse().getId(), course);
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(responseData);

        } catch (Exception e) {
            System.err.println("Error occurred while fetching my course: " + e.getMessage());
            throw new RuntimeException("Error fetching my course", e);
        }
    }

    public String getVocabularyProgressByUserIdAndCourseId(Integer userId, Integer courseId) {
        try {
            // Fetch the course for the user
            MyCourse myCourse = myCourseMapper.getMyCourseByUserIdAndCourseId(userId, courseId);
            if (myCourse == null) {
                return "{}"; // Return empty JSON if course not found
            }

            // Fetch all topics associated with the course
            List<VocabularyTopic> topics = vocabularyTopicMapper.getAllByCourseId(courseId);
            if (topics == null || topics.isEmpty()) {
                return "{}"; // Return empty JSON if no topics found
            }

            // Prepare response data map for the course
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("course_id", myCourse.getCourse().getId());
            responseData.put("course_name", myCourse.getCourse().getCourse_name());
            responseData.put("course_image", myCourse.getCourse().getCourse_image());
            responseData.put("course_description", myCourse.getCourse().getCourse_description());
            responseData.put("course_level", myCourse.getCourse().getCourse_level());

            // Initialize variables for tracking progress
            int learnedTopics = 0;
            int totalTopics = 0;

            // Iterate through each topic to calculate progress
            for (VocabularyTopic topic : topics) {
                int learnedWords = vocabularyProgressMapper.countVocabularyLearned(userId, topic.getId());
                int notLearnedWords = vocabularyProgressMapper.countVocabularyNotLearned(userId, topic.getId());

                int totalWords = learnedWords + notLearnedWords;
                int topic_progress = (totalWords == 0) ? 0 : (int) Math.ceil((double) learnedWords * 100 / totalWords);

                if (topic_progress >= 80)
                    learnedTopics++;
                totalTopics++;

                // Store topic progress information
                Map<String, Object> topicProgress = new HashMap<>();
                topicProgress.put("topic_id", topic.getId());
                topicProgress.put("topic_name", topic.getTopic_name());
                topicProgress.put("topic_image", topic.getTopic_image());
                topicProgress.put("topic_description", topic.getTopic_description());
                topicProgress.put("learned_words", learnedWords);
                topicProgress.put("total_words", totalWords);
                topicProgress.put("topic_progress", topic_progress);

                // Add topic-specific data to response
                responseData.put("topic_" + topic.getId(), topicProgress);
            }

            // Calculate and store course progress
            responseData.put("learned_topic", learnedTopics);
            responseData.put("total_topic", totalTopics);
            responseData.put("course_progress",
                    totalTopics == 0 ? 0 : (int) Math.ceil((double) learnedTopics * 100 / totalTopics));

            // Convert response data to JSON and return
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(responseData);

        } catch (Exception e) {
            System.err.println("Error occurred while fetching vocabulary progress: " + e.getMessage());
            throw new RuntimeException("Error fetching vocabulary progress", e);
        }
    }

    public String getGrammarProgressByUserIdAndCourseId(Integer userId, Integer courseId) {
        try {
            // Fetch course
            MyCourse myCourse = myCourseMapper.getMyCourseByUserIdAndCourseId(userId, courseId);
            if (myCourse == null) {
                return "{}"; // Return empty JSON if course not found
            }

            // Prepare response data for the course
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("course_id", myCourse.getCourse().getId());
            responseData.put("course_name", myCourse.getCourse().getCourse_name());
            responseData.put("course_image", myCourse.getCourse().getCourse_image());
            responseData.put("course_description", myCourse.getCourse().getCourse_description());
            responseData.put("course_level", myCourse.getCourse().getCourse_level());

            // Calculate grammar progress
            int learnedGrammar = grammarProgressMapper.countLearnedGrammar(userId, courseId);
            int notLearnedGrammar = grammarProgressMapper.countNotLearnedGrammar(userId, courseId);
            int grammar_progress = (learnedGrammar + notLearnedGrammar) == 0 ? 0
                    : (int) Math.ceil((double) learnedGrammar * 100 / (learnedGrammar + notLearnedGrammar));

            responseData.put("progress", grammar_progress);

            // Fetch all grammar lessons by course
            List<Grammar> listGrammar = grammarMapper.getAllByCourseId(courseId);
            for (Grammar grammar : listGrammar) {
                GrammarProgress grammarProgress = grammarProgressMapper.getGrammarProgressByUserIdAndGrammarId(userId,
                        grammar.getId());

                if (grammarProgress == null) {
                    continue; // Skip if no progress found for the grammar lesson
                }

                List<QuestionGrammar> listQuestion = questionGrammarMapper.getQuestionsByGrammarId(grammar.getId());

                // Add grammar lesson details
                Map<String, Object> content = new HashMap<>();
                content.put("id", grammar.getId());
                content.put("lession", grammar.getLesson_number());
                content.put("name", grammar.getGrammar_name());
                content.put("description", grammar.getGrammar_description());
                content.put("learned", grammarProgress.getIs_learned_theory() && grammarProgress.getIs_finish_quiz());

                // Add theory details
                Map<String, Object> theory = new HashMap<>();
                theory.put("id", grammar.getId());
                theory.put("name", grammar.getGrammar_name());
                theory.put("explanation", grammar.getExplanation());
                theory.put("example", grammar.getExample());
                theory.put("learned", grammarProgress.getIs_learned_theory());
                content.put("theory", theory);

                // Add quiz details if available
                if (!listQuestion.isEmpty()) {
                    Map<String, Object> quiz = new HashMap<>();
                    quiz.put("passed", grammarProgress.getIs_finish_quiz());

                    int number_question = 1;
                    for (QuestionGrammar questionGrammar : listQuestion) {
                        // Prepare options list based on the quiz type
                        List<String> options = new ArrayList<>();

                        if ("multichoice".equals(questionGrammar.getQuiz_type())) {
                            options.add(questionGrammar.getCorrect_answer());
                            String[] incorrectAnswers = questionGrammar.getIncorrect_answer()
                                    .replaceAll("[\\[\\]\"]", "").split(",");
                            options.addAll(Arrays.asList(incorrectAnswers));
                            Collections.shuffle(options);
                        }

                        // Add question details
                        Map<String, Object> question = new HashMap<>();
                        question.put("id", questionGrammar.getId());
                        question.put("type", questionGrammar.getQuiz_type());
                        question.put("question_text", questionGrammar.getQuestion_text());
                        question.put("correct_answer", questionGrammar.getCorrect_answer());
                        // If it's essay, options should be empty, otherwise, use the options list
                        question.put("options",
                                "essay".equals(questionGrammar.getQuiz_type()) ? Collections.emptyList() : options);

                        quiz.put("question_" + number_question, question);
                        number_question++;
                    }
                    content.put("quiz", quiz);
                }

                responseData.put("lession_" + grammar.getLesson_number(), content);
            }

            // Convert response data to JSON
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(responseData);

        } catch (Exception e) {
            System.err.println("Error occurred while fetching course progress: " + e.getMessage());
            throw new RuntimeException("Error fetching progress", e);
        }
    }

}