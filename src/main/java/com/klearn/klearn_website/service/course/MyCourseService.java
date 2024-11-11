package com.klearn.klearn_website.service.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klearn.klearn_website.dto.dtoin.MyCourseDTOIn;
import com.klearn.klearn_website.dto.dtoout.CourseWithCountDTOOut;
import com.klearn.klearn_website.mapper.MyCourseMapper;
import com.klearn.klearn_website.model.*;
import com.klearn.klearn_website.service.grammar.GrammarProgressService;
import com.klearn.klearn_website.service.grammar.GrammarService;
import com.klearn.klearn_website.service.grammar.QuestionGrammarService;
import com.klearn.klearn_website.service.vocabulary.VocabularyProgressService;
import com.klearn.klearn_website.service.vocabulary.VocabularyTopicService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class MyCourseService {
    private final MyCourseMapper myCourseMapper;
    private final UserService userService;
    private final CourseService courseService;
    private final VocabularyTopicService vocabularyTopicService;
    private final VocabularyProgressService vocabularyProgressService;
    private final GrammarService grammarService;
    private final GrammarProgressService grammarProgressService;
    private final QuestionGrammarService questionGrammarService;
    private final ObjectMapper objectMapper;

    /**
     * Insert a new MyCourse entry.
     *
     * @param myCourseDTOIn The DTO containing details for the new MyCourse.
     */
    public void insertMyCourse(MyCourseDTOIn myCourseDTOIn) {
        // Check if user exists
        User user = userService.getUserById(myCourseDTOIn.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + myCourseDTOIn.getUser_id()));
        // Check if course exists
        Course course = courseService.getCourseById(myCourseDTOIn.getCourse_id())
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + myCourseDTOIn.getCourse_id()));

        // Create new MyCourse instance
        MyCourse myCourse = new MyCourse(
                new MyCourse.MyCourseId(user.getId(), course.getId()),
                LocalDateTime.now(),
                myCourseDTOIn.getPayment_status(),
                LocalDateTime.now(),
                false,
                user,
                course);

        // Insert into the database
        MyCourse existCourse = myCourseMapper.getMyCourseByUserIdAndCourseId(myCourseDTOIn.getUser_id(),
                myCourseDTOIn.getCourse_id());

        if (existCourse != null) {
            myCourseMapper.updateMyCourse(myCourse);
        } else {
            myCourseMapper.insertMyCourse(myCourse);

            List<VocabularyTopic> listTopics = vocabularyTopicService.getVocabularyTopicsByCourseId(course.getId());

            for (VocabularyTopic topic : listTopics) {
                vocabularyProgressService.getVocabularyProgressByUserIdAndTopicId(user.getId(), topic.getId());
            }

            grammarProgressService.getGrammarProgressByUserIdAndGrammarId(user.getId(), course.getId());
        }

    }

    /**
     * 
     * @param userId
     * @return
     */
    public List<MyCourse> getAllCourseByUserId(Integer userId) {
        return myCourseMapper.getMyCourseByUserId(userId);
    }

    /**
     * 
     * @param userId
     * @return
     */
    public Boolean existsMyCourseByUserIdAndCourseId(Integer userId, Integer courseId) {
        return myCourseMapper.existsMyCourseByUserIdAndCourseId(userId, courseId);
    }

    /*
     * 
     */
    public String myCoursePaymentStatus(Integer userId, Integer courseId) {
        MyCourse myCourse = myCourseMapper.getMyCourseByUserIdAndCourseId(userId, courseId);
        if (myCourse == null) {
            return "";
        }
        return myCourse.getPayment_status();
    }
    /**
     * Get MyCourse details by user ID.
     *
     * @param userId The ID of the user.
     * @return JSON representation of the user's course details and progress.
     */
    public String getMyCourseByUserId(Integer userId) {
        try {
            List<MyCourse> listMyCourses = myCourseMapper.getMyCourseByUserId(userId);
            if (listMyCourses.isEmpty()) {
                return "[]";
            }
    
            List<Map<String, Object>> responseData = new ArrayList<>();
    
            for (MyCourse myCourse : listMyCourses) {
                Map<String, Object> courseData = new HashMap<>();
                courseData.put("id", myCourse.getCourse().getId());
                courseData.put("name", myCourse.getCourse().getCourse_name());
    
                // Calculate the progress of Grammar
                int learnedGrammar = grammarProgressService.countLearnedGrammar(userId, myCourse.getCourse().getId());
                int notLearnedGrammar = grammarProgressService.countNotLearnedGrammar(userId, myCourse.getCourse().getId());
                int grammarProgress = (learnedGrammar + notLearnedGrammar) == 0 ? 0
                        : (int) Math.ceil((double) learnedGrammar * 100 / (learnedGrammar + notLearnedGrammar));
    
                // Calculate the progress of Vocabulary
                int vocabProgress = 0;
                List<VocabularyTopic> topics = vocabularyTopicService
                        .getVocabularyTopicsByCourseId(myCourse.getCourse().getId());
                if (!topics.isEmpty()) {
                    int learnedTopics = 0;
                    int totalTopics = 0;
                    for (VocabularyTopic topic : topics) {
                        int learnedWords = vocabularyProgressService.countVocabularyLearned(userId, topic.getId());
                        int notLearnedWords = vocabularyProgressService.countVocabularyNotLearned(userId, topic.getId());
                        int totalWords = learnedWords + notLearnedWords;
                        int topicProgress = (totalWords == 0) ? 0
                                : (int) Math.ceil((double) learnedWords * 100 / totalWords);
    
                        if (topicProgress >= 80) {
                            learnedTopics++;
                        }
                        totalTopics++;
                    }
                    vocabProgress = totalTopics == 0 ? 0 : (int) Math.ceil((double) learnedTopics * 100 / totalTopics);
                }
    
                // Calculate overall course progress
                courseData.put("progress", (int) Math.ceil(((double) grammarProgress + (double) vocabProgress) / 2));
                responseData.add(courseData); // Thêm vào danh sách thay vì một map
            }
    
            return objectMapper.writeValueAsString(responseData);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching my course", e);
        }
    }
    

    /**
     * Get Vocabulary progress by user ID and course ID.
     *
     * @param userId   The ID of the user.
     * @param courseId The ID of the course.
     * @return JSON representation of the user's vocabulary progress.
     */
    public String getVocabularyProgressByUserIdAndCourseId(Integer userId, Integer courseId) {
        try {
            // Fetch the course for the user
            MyCourse myCourse = myCourseMapper.getMyCourseByUserIdAndCourseId(userId, courseId);
            if (myCourse == null) {
                return "{}"; // Return empty JSON if course not found
            }

            // Fetch all topics associated with the course
            List<VocabularyTopic> topics = vocabularyTopicService.getVocabularyTopicsByCourseId(courseId);
            if (topics.isEmpty()) {
                return "{}"; // Return empty JSON if no topics found
            }

            Map<String, Object> responseData = prepareVocabularyProgressData(userId, myCourse, topics);
            return objectMapper.writeValueAsString(responseData);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching vocabulary progress", e);
        }
    }

    /**
     * Prepare the vocabulary progress data for the response.
     *
     * @param userId   The ID of the user.
     * @param myCourse The MyCourse instance.
     * @param topics   The list of VocabularyTopic.
     * @return A map with the vocabulary progress details.
     */
    private Map<String, Object> prepareVocabularyProgressData(Integer userId, MyCourse myCourse,
            List<VocabularyTopic> topics) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("course_id", myCourse.getCourse().getId());
        responseData.put("course_name", myCourse.getCourse().getCourse_name());
        responseData.put("course_image", myCourse.getCourse().getCourse_image());
        responseData.put("course_description", myCourse.getCourse().getCourse_description());
        responseData.put("course_level", myCourse.getCourse().getCourse_level());
        responseData.put("payment_status", myCourse.getPayment_status());

        int learnedTopics = 0;
        int totalTopics = 0;
        List<Map<String, Object>> topicProgressArray = new ArrayList<>();

        for (VocabularyTopic topic : topics) {
            int learnedWords = vocabularyProgressService.countVocabularyLearned(userId, topic.getId());
            int notLearnedWords = vocabularyProgressService.countVocabularyNotLearned(userId, topic.getId());
            int totalWords = learnedWords + notLearnedWords;
            int topicProgress = (totalWords == 0) ? 0 : (int) Math.ceil((double) learnedWords * 100 / totalWords);

            if (topicProgress >= 80) {
                learnedTopics++;
            }
            totalTopics++;

            Map<String, Object> topicProgressData = new HashMap<>();
            topicProgressData.put("total_word", totalWords);
            topicProgressData.put("topic_id", topic.getId());
            topicProgressData.put("topic_name", topic.getTopic_name());
            topicProgressData.put("topic_progress", topicProgress);
            topicProgressData.put("topic_description", topic.getTopic_description());

            topicProgressArray.add(topicProgressData);
        }

        responseData.put("learned_topic", learnedTopics);
        responseData.put("total_topic", totalTopics);
        responseData.put("course_progress",
                totalTopics == 0 ? 0 : (int) Math.ceil((double) learnedTopics * 100 / totalTopics));
        responseData.put("topics", topicProgressArray);

        return responseData;
    }

    /**
     * Get detailed grammar progress by user ID and course ID, including quiz
     * questions.
     *
     * @param userId   The ID of the user.
     * @param courseId The ID of the course.
     * @return JSON representation of the user's detailed grammar progress,
     *         including questions.
     */
    public String getDetailedGrammarProgressByUserIdAndCourseId(Integer userId, Integer courseId) {
        try {
            MyCourse myCourse = myCourseMapper.getMyCourseByUserIdAndCourseId(userId, courseId);
            if (myCourse == null) {
                return "[]"; // Return empty JSON if course not found
            }

            grammarProgressService.getGrammarProgressByUserIdAndCourseId(userId, courseId);

            Map<String, Object> responseData = prepareDetailedGrammarProgressData(userId, myCourse);
            return objectMapper.writeValueAsString(responseData);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching detailed grammar progress", e);
        }
    }

    /**
     * Prepare detailed grammar progress data, including quiz questions.
     *
     * @param userId   The ID of the user.
     * @param myCourse The MyCourse instance.
     * @return A map with detailed grammar progress, including quiz questions.
     */
    private Map<String, Object> prepareDetailedGrammarProgressData(Integer userId, MyCourse myCourse) {
        Map<String, Object> responseData = new HashMap<>();

        List<Map<String, Object>> listLesson = new ArrayList<>(); // Changed to List for array-like structure

        responseData.put("course_id", myCourse.getCourse().getId());
        responseData.put("course_name", myCourse.getCourse().getCourse_name());
        responseData.put("payment_status", myCourse.getPayment_status());

        int learnedGrammar = grammarProgressService.countLearnedGrammar(userId, myCourse.getCourse().getId());
        int notLearnedGrammar = grammarProgressService.countNotLearnedGrammar(userId, myCourse.getCourse().getId());
        int grammarProgress = (learnedGrammar + notLearnedGrammar) == 0 ? 0
                : (int) Math.ceil((double) learnedGrammar * 100 / (learnedGrammar + notLearnedGrammar));
        responseData.put("progress", grammarProgress);

        List<Grammar> grammarList = grammarService.getGrammarByCourseId(myCourse.getCourse().getId());
        for (Grammar grammar : grammarList) {
            Optional<GrammarProgress> grammarProgressEntity = grammarProgressService
                    .getGrammarProgressByUserIdAndGrammarId(userId, grammar.getId());

            if (grammarProgressEntity.isEmpty()) {
                continue; // Skip if no progress found for the grammar lesson
            }
            GrammarProgress progress = grammarProgressEntity.get();

            List<QuestionGrammar> questionList = questionGrammarService.getAllQuestionsByGrammarId(grammar.getId());

            Map<String, Object> content = new HashMap<>();
            content.put("id", grammar.getId());
            content.put("lesson", grammar.getLesson_number());
            content.put("name", grammar.getGrammar_name());
            content.put("description", grammar.getGrammar_description());
            content.put("learned", progress.getIs_learned_theory() && progress.getIs_finish_quiz());

            // Add theory details
            Map<String, Object> theory = new HashMap<>();
            theory.put("id", grammar.getId());
            theory.put("name", grammar.getGrammar_name());
            theory.put("explanation", grammar.getExplanation());
            theory.put("example", grammar.getExample());
            theory.put("learned", progress.getIs_learned_theory());
            content.put("theory", theory);

            // Add quiz details if available
            if (!questionList.isEmpty()) {
                Map<String, Object> quiz = new HashMap<>();
                quiz.put("passed", progress.getIs_finish_quiz());
                quiz.put("failed", progress.getIs_failed_quiz());

                // List to store questions as an array
                List<Map<String, Object>> questionsArray = new ArrayList<>();

                List<QuestionGrammar> selectedQuestions = questionList;

                // Select a random subset of 5 questions if there are more than 5
                if (questionList.size() > 5) {
                    Collections.shuffle(questionList, new Random());
                    selectedQuestions = questionList.subList(0, 5);
                }

                for (QuestionGrammar question : selectedQuestions) {
                    // Prepare options based on quiz type
                    List<String> options = prepareQuestionOptions(question);

                    // Add question details
                    Map<String, Object> questionData = new HashMap<>();
                    questionData.put("id", question.getId());
                    questionData.put("type", question.getQuiz_type());
                    questionData.put("question_text", question.getQuestion_text());
                    questionData.put("correct_answer", question.getCorrect_answer());
                    questionData.put("options",
                            "essay".equals(question.getQuiz_type()) ? Collections.emptyList() : options);

                    // Add question to the array
                    questionsArray.add(questionData);
                }
                quiz.put("questions", questionsArray); // Add the questions array to the quiz
                content.put("quiz", quiz);
            }

            listLesson.add(content); // Add each lesson's content to the list
        }

        responseData.put("list_lesson", listLesson); // Use listLesson as a List

        return responseData;
    }

    /**
     * Prepare options for a given question based on its quiz type.
     *
     * @param question The QuestionGrammar instance.
     * @return A list of options for the question.
     */
    private List<String> prepareQuestionOptions(QuestionGrammar question) {
        List<String> options = new ArrayList<>();
        if ("multichoice".equals(question.getQuiz_type())) {
            options.add(question.getCorrect_answer());
            String[] incorrectAnswers = question.getIncorrect_answer().replaceAll("[\\[\\]\"]", "").split(",");
            options.addAll(Arrays.asList(incorrectAnswers));
            Collections.shuffle(options);
        }
        return options;
    }

    public List<CourseWithCountDTOOut> getCourseWithUserCount() {
        return myCourseMapper.getCourseWithUserCount();
    }
}
