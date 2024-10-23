package com.klearn.klearn_website.service.quiz;

import org.springframework.stereotype.Service;
import com.klearn.klearn_website.dto.dtoout.GrammarQuestionDTOOut;
import com.klearn.klearn_website.dto.dtoout.VocabularyQuestionDTOOut;
import com.klearn.klearn_website.model.*;
import com.klearn.klearn_website.service.course.CourseService;
import com.klearn.klearn_website.service.grammar.GrammarService;
import com.klearn.klearn_website.service.grammar.QuestionGrammarService;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.service.vocabulary.VocabularyService;
import com.klearn.klearn_website.service.vocabulary.VocabularyTopicService;

import java.util.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ComprehensiveQuizService {
    private final UserService userService;
    private final CourseService courseService;
    private final VocabularyService vocabularyService;
    private final VocabularyTopicService vocabularyTopicService;
    private final GrammarService grammarService;
    private final QuestionGrammarService questionGrammarService;
    private final Random random = new Random();

    /**
     * Generates a vocabulary comprehensive quiz for a given user and course.
     *
     * @param userId   The ID of the user.
     * @param courseId The ID of the course.
     * @return A list of up to 25 VocabularyQuestionDTOOut representing the quiz questions.
     */
    public List<VocabularyQuestionDTOOut> getVocabComprehensiveQuiz(Integer userId, Integer courseId) {
        validateUserAndCourseExist(userId, courseId);

        List<VocabularyTopic> vocabularyTopics = vocabularyTopicService.getVocabularyTopicsByCourseId(courseId);

        // Get all vocabulary related to the course's topics
        List<Vocabulary> vocabularyList = new ArrayList<>();
        for (VocabularyTopic topic : vocabularyTopics) {
            vocabularyList.addAll(vocabularyService.getVocabularyByTopicId(topic.getId()));
        }

        return createVocabQuiz(vocabularyList);
    }

    /**
     * Generates a grammar comprehensive quiz for a given user and course.
     *
     * @param userId   The ID of the user.
     * @param courseId The ID of the course.
     * @return A list of up to 25 GrammarQuestionDTOOut representing the quiz questions.
     */
    public List<GrammarQuestionDTOOut> getGrammarComprehensiveQuiz(Integer userId, Integer courseId) {
        validateUserAndCourseExist(userId, courseId);

        // Retrieve grammar topics and their associated questions
        List<Grammar> grammarList = grammarService.getGrammarByCourseId(courseId);
        List<QuestionGrammar> questionGrammarList = new ArrayList<>();
        for (Grammar grammar : grammarList) {
            questionGrammarList.addAll(questionGrammarService.getAllQuestionsByGrammarId(grammar.getId()));
        }

        return createGrammarQuiz(questionGrammarList);
    }

    /**
     * Validates if the user and course exist.
     *
     * @param userId   The ID of the user.
     * @param courseId The ID of the course.
     */
    private void validateUserAndCourseExist(Integer userId, Integer courseId) {
        userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        courseService.getCourseById(courseId).orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    }

    /**
     * Creates a vocabulary quiz from a list of vocabulary.
     *
     * @param vocabularyList The list of vocabulary to generate the quiz from.
     * @return A list of VocabularyQuestionDTOOut for the quiz.
     */
    private List<VocabularyQuestionDTOOut> createVocabQuiz(List<Vocabulary> vocabularyList) {
        if (vocabularyList.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(vocabularyList);
        List<Vocabulary> limitedVocabulary = vocabularyList.size() > 25 ? vocabularyList.subList(0, 25) : vocabularyList;

        List<VocabularyQuestionDTOOut> quizQuestions = new ArrayList<>();
        for (Vocabulary vocabulary : limitedVocabulary) {
            String questionType = random.nextBoolean() ? "multichoice" : "essay";
            quizQuestions.add(createVocabularyQuestion(vocabulary, questionType, vocabularyList));
        }

        return quizQuestions;
    }

    /**
     * Creates a grammar quiz from a list of grammar questions.
     *
     * @param questionGrammarList The list of grammar questions.
     * @return A list of GrammarQuestionDTOOut for the quiz.
     */
    private List<GrammarQuestionDTOOut> createGrammarQuiz(List<QuestionGrammar> questionGrammarList) {
        if (questionGrammarList.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(questionGrammarList);
        List<QuestionGrammar> limitedQuestionGrammar = questionGrammarList.size() > 25 ? questionGrammarList.subList(0, 25) : questionGrammarList;

        List<GrammarQuestionDTOOut> quizQuestions = new ArrayList<>();
        for (QuestionGrammar questionGrammar : limitedQuestionGrammar) {
            quizQuestions.add(createGrammarQuestion(questionGrammar));
        }

        return quizQuestions;
    }

    /**
     * Creates a VocabularyQuestionDTOOut for a given vocabulary.
     *
     * @param vocabulary   The vocabulary.
     * @param questionType The type of the question ("multichoice" or "essay").
     * @param allVocabulary The list of all vocabulary to generate options.
     * @return A VocabularyQuestionDTOOut.
     */
    private VocabularyQuestionDTOOut createVocabularyQuestion(Vocabulary vocabulary, String questionType, List<Vocabulary> allVocabulary) {
        List<String> options = questionType.equals("multichoice") ? generateOptions(vocabulary.getDefinition(), allVocabulary) : Collections.emptyList();

        return new VocabularyQuestionDTOOut(
                vocabulary.getId(),
                questionType,
                vocabulary.getWord(),
                vocabulary.getDefinition(),
                options
        );
    }

    /**
     * Creates a GrammarQuestionDTOOut for a given QuestionGrammar.
     *
     * @param question The QuestionGrammar instance.
     * @return A GrammarQuestionDTOOut.
     */
    private GrammarQuestionDTOOut createGrammarQuestion(QuestionGrammar question) {
        List<String> options = prepareGrammarOptions(question);

        return new GrammarQuestionDTOOut(
                question.getId(),
                question.getQuiz_type(),
                question.getQuestion_text(),
                question.getCorrect_answer(),
                "essay".equals(question.getQuiz_type()) ? Collections.emptyList() : options
        );
    }

    /**
     * Generates options for a multiple-choice vocabulary question.
     *
     * @param correctDefinition The correct definition.
     * @param allVocabulary     The list of all vocabulary.
     * @return A list of options including the correct definition.
     */
    private List<String> generateOptions(String correctDefinition, List<Vocabulary> allVocabulary) {
        List<String> options = new ArrayList<>();
        options.add(correctDefinition);

        List<String> distractors = new ArrayList<>();
        for (Vocabulary vocab : allVocabulary) {
            if (!vocab.getDefinition().equals(correctDefinition)) {
                distractors.add(vocab.getDefinition());
            }
        }

        Collections.shuffle(distractors);
        for (int i = 0; i < 3 && i < distractors.size(); i++) {
            options.add(distractors.get(i));
        }

        Collections.shuffle(options);
        return options;
    }

    /**
     * Prepares options for a grammar question.
     *
     * @param question The QuestionGrammar instance.
     * @return A list of options for the question.
     */
    private List<String> prepareGrammarOptions(QuestionGrammar question) {
        List<String> options = new ArrayList<>();
        if ("multichoice".equals(question.getQuiz_type())) {
            options.add(question.getCorrect_answer());
            String[] incorrectAnswers = question.getIncorrect_answer().replaceAll("[\\[\\]\"]", "").split(",");
            options.addAll(Arrays.asList(incorrectAnswers));
            Collections.shuffle(options);
        }
        return options;
    }
}
