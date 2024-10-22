package com.klearn.klearn_website.service.quiz;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.dto.dtoout.VocabularyQuestionDTOOut;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.model.Vocabulary;
import com.klearn.klearn_website.model.VocabularyTopic;
import com.klearn.klearn_website.service.course.CourseService;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.service.vocabulary.VocabularyService;
import com.klearn.klearn_website.service.vocabulary.VocabularyTopicService;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ComprehensiveQuizService {
    private final UserService userService;
    private final CourseService courseService;
    private final VocabularyService vocabularyService;
    private final VocabularyTopicService vocabularyTopicService;
    private final Random random = new Random();

    /**
     * Get Vocabulary Comprehensive Quiz by UserId and CourseId.
     *
     * @param userId   The ID of the user.
     * @param courseId The ID of the course.
     * @return A list of up to 25 VocabularyQuestionDTOOut representing the quiz
     *         questions.
     */

    public List<VocabularyQuestionDTOOut> getVocabComprehensiveQuiz(Integer userId, Integer courseId) {
        // Check if user exists
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Check if course exists
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        List<VocabularyTopic> listVocabularyTopic = vocabularyTopicService.getVocabularyTopicsByCourseId(courseId);

        // Get vocabulary for all topics in the course
        List<Vocabulary> listVocabulary = new ArrayList<>();
        for (VocabularyTopic topic : listVocabularyTopic) {
            listVocabulary.addAll(vocabularyService.getVocabularyByTopicId(topic.getId()));
        }
        
        // If the list is empty, return an empty list
        if (listVocabulary.isEmpty()) {
            return Collections.emptyList();
        }

        // Shuffle the list to randomize the selection of questions
        Collections.shuffle(listVocabulary);

        List<Vocabulary> limitedVocabulary = listVocabulary.size() > 25 ? listVocabulary.subList(0, 25)
                : listVocabulary;

        // Generate the quiz questions
        List<VocabularyQuestionDTOOut> quizQuestions = new ArrayList<>();

        for (Vocabulary vocabulary : limitedVocabulary) {
            String questionType = random.nextBoolean() ? "multichoice" : "essay";
            VocabularyQuestionDTOOut question = createQuestion(vocabulary, questionType, listVocabulary);
            quizQuestions.add(question);
        }

        return quizQuestions;
    }

    /**
     * Creates a quiz question for a given vocabulary.
     *
     * @param vocabulary    The vocabulary word to create the question for.
     * @param questionType  The type of question ("multichoice" or "essay").
     * @param allVocabulary The list of all vocabulary items to use for generating
     *                      options.
     * @return A VocabularyQuestionDTOOut representing the question.
     */
    private VocabularyQuestionDTOOut createQuestion(Vocabulary vocabulary, String questionType,
            List<Vocabulary> allVocabulary) {
        Integer vocabularyId = vocabulary.getId();
        String word = vocabulary.getWord();
        String definition = vocabulary.getDefinition();

        // Create options for multiple-choice questions
        List<String> options = questionType.equals("multichoice") ? generateOptions(definition, allVocabulary)
                : Collections.emptyList();

        // Return the formatted question
        return new VocabularyQuestionDTOOut(
                vocabularyId,
                questionType,
                word,
                definition,
                options);
    }

    /**
     * Generates options for a multiple-choice question.
     *
     * @param correctDefinition The correct definition for the vocabulary word.
     * @param allVocabulary     List of all vocabulary items to use as possible
     *                          incorrect options.
     * @return A list of options including the correct definition.
     */
    private List<String> generateOptions(String correctDefinition, List<Vocabulary> allVocabulary) {
        List<String> options = new ArrayList<>();
        options.add(correctDefinition);

        // Collect all other definitions as distractors
        List<String> distractors = new ArrayList<>();
        for (Vocabulary vocab : allVocabulary) {
            if (!vocab.getDefinition().equals(correctDefinition)) {
                distractors.add(vocab.getDefinition());
            }
        }

        // Shuffle the distractors to ensure randomness
        Collections.shuffle(distractors);

        // Add up to three distractors to the options
        for (int i = 0; i < 3 && i < distractors.size(); i++) {
            options.add(distractors.get(i));
        }

        // Shuffle the final options list
        Collections.shuffle(options);
        return options;
    }
}
