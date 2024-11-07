package com.klearn.klearn_website.service.quiz;

import com.klearn.klearn_website.dto.dtoin.GrammarQuizAnswerDTOIn;
import com.klearn.klearn_website.dto.dtoout.GrammarAnswerDTOOut;
import com.klearn.klearn_website.mapper.ComprehensiveTestResultsMapper;
import com.klearn.klearn_website.model.ComprehensiveGrammarTestAnswer;
import com.klearn.klearn_website.model.ComprehensiveTestResults;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.model.QuestionGrammar;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.course.CourseService;
import com.klearn.klearn_website.service.grammar.GrammarService;
import com.klearn.klearn_website.service.grammar.QuestionGrammarService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ComprehensiveTestResultsService {

    private final ComprehensiveTestResultsMapper comprehensiveTestResultsMapper;
    private final CourseService courseService;
    private final UserService userService;
    private final QuestionGrammarService questionGrammarService;
    private final GrammarService grammarService;
    private final ComprehensiveGrammarTestAnswerService comprehensiveGrammarTestAnswerService;

    public void addComprehensiveTestResult(ComprehensiveTestResults comprehensiveTestResults) {
        comprehensiveTestResultsMapper.insertComprehensiveTestResults(comprehensiveTestResults);
    }

    public ComprehensiveTestResults getMostRecentTestByCourseUserAndType(Integer courseId, Integer userId,
            String testType) {
        return comprehensiveTestResultsMapper.findMostRecentTestByCourseUserAndType(courseId, userId, testType);
    }

    public void processGrammarQuizAnswers(List<GrammarQuizAnswerDTOIn> grammarQuizAnswers, Integer courseId,
            Integer duration, Integer userId) {

        int no_correct_answer = 0;
        int no_incorrect_answer = 0;

        // Calculate correct and incorrect answers
        for (GrammarQuizAnswerDTOIn grammarAnswer : grammarQuizAnswers) {
            if (Boolean.TRUE.equals(grammarAnswer.getIs_correct())) {
                no_correct_answer++;
            } else {
                no_incorrect_answer++;
            }
        }

        // Fetch the course and user by their IDs
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Create and save a new ComprehensiveTestResults instance
        ComprehensiveTestResults testResults = new ComprehensiveTestResults();
        testResults.setTest_type("grammar");
        testResults.setTest_date(LocalDateTime.now());
        testResults.setNo_correct_questions(no_correct_answer);
        testResults.setNo_incorrect_questions(no_incorrect_answer);
        testResults.setDuration(duration);
        testResults.setLast_modified(LocalDateTime.now());
        testResults.setIs_deleted(false);
        testResults.setUser(user);
        testResults.setCourse(course);

        // Save the test result
        addComprehensiveTestResult(testResults);

        // Process each answer and associate it with the saved test result
        for (GrammarQuizAnswerDTOIn grammarAnswer : grammarQuizAnswers) {
            Grammar grammar = grammarService.getGrammarById(grammarAnswer.getGrammar_id())
                    .orElseThrow(
                            () -> new RuntimeException("Grammar not found with ID: " + grammarAnswer.getGrammar_id()));
            QuestionGrammar question = questionGrammarService.getQuestionById(grammarAnswer.getQuestion_id());

            ComprehensiveGrammarTestAnswer answer = new ComprehensiveGrammarTestAnswer();
            answer.setIs_deleted(false);
            answer.setComprehensiveTestResults(testResults);
            answer.setGrammar(grammar);
            answer.setQuestionGrammar(question);
            answer.setUser_answer(grammarAnswer.getUser_answer());
            answer.setLast_modified(LocalDateTime.now());
            answer.setIs_correct(grammarAnswer.getIs_correct());

            // Convert options array to a comma-separated string if it exists
            if (grammarAnswer.getOptions() != null) {
                answer.setOptions(String.join(",", grammarAnswer.getOptions()));
            } else {
                answer.setOptions(null);
            }

            // Save each answer associated with the test result
            comprehensiveGrammarTestAnswerService.insertComprehensiveGrammarTestAnswer(answer);
        }
    }

    public List<GrammarAnswerDTOOut> getGrammarAnswerByTestId(Integer testId) {
        List<ComprehensiveGrammarTestAnswer> listAnswer = comprehensiveGrammarTestAnswerService
                .getComprehensiveGrammarTestAnswersByTestId(testId);
        List<GrammarAnswerDTOOut> dtoOutList = new ArrayList<>();

        for (ComprehensiveGrammarTestAnswer answer : listAnswer) {
            GrammarAnswerDTOOut dtoOut = new GrammarAnswerDTOOut();
            dtoOut.setAnswer_id(answer.getId());
            dtoOut.setUser_answer(answer.getUser_answer());
            dtoOut.setIs_correct(answer.getIs_correct());
            dtoOut.setQuestion_id(answer.getQuestionGrammar().getId());
            dtoOut.setType(answer.getQuestionGrammar().getQuiz_type());
            dtoOut.setQuestion_text(answer.getQuestionGrammar().getQuestion_text());
            dtoOut.setCorrect_answer(answer.getQuestionGrammar().getCorrect_answer());
            dtoOut.setGrammar_id(answer.getGrammar().getId());
            dtoOut.setGrammar_lesson_number(answer.getGrammar().getLesson_number());
            dtoOut.setGrammar_name(answer.getGrammar().getGrammar_name());

            // Convert options from comma-separated string to list
            if (answer.getOptions() != null) {
                List<String> optionsList = Arrays.asList(answer.getOptions().split(","));
                dtoOut.setOptions(optionsList);
            } else {
                dtoOut.setOptions(new ArrayList<>()); // Empty list if no options
            }

            dtoOutList.add(dtoOut);
        }

        return dtoOutList;
    }
}
