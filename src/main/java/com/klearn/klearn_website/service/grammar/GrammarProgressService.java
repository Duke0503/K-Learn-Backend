package com.klearn.klearn_website.service.grammar;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.mapper.CourseMapper;
import com.klearn.klearn_website.mapper.GrammarMapper;
import com.klearn.klearn_website.mapper.GrammarProgressMapper;
import com.klearn.klearn_website.mapper.QuestionGrammarMapper;
import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.model.GrammarProgress.GrammarProgressId;
import com.klearn.klearn_website.model.QuestionGrammar;
import com.klearn.klearn_website.model.GrammarProgress;
import com.klearn.klearn_website.model.User;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GrammarProgressService {
    private final UserMapper userMapper;
    private final GrammarProgressMapper grammarProgressMapper;
    private final CourseMapper courseMapper;
    private final GrammarMapper grammarMapper;
    private final QuestionGrammarMapper questionGrammarMapper;

    public List<GrammarProgress> getGrammarProgressByUserIdAndCourseId(Integer userId, Integer courseId) {
        User user = userMapper.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        Course course = courseMapper.findCourseById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }

        List<Grammar> listGrammar = grammarMapper.getAllByCourseId(courseId);

        for (Grammar grammar : listGrammar) {
            Boolean is_finish_quiz = false;
            List<QuestionGrammar> listQuestion = questionGrammarMapper.getQuestionsByGrammarId(grammar.getId());
            if (listQuestion.isEmpty()) {
                is_finish_quiz = true;
            }

            if (!grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammar.getId(), courseId)) {
                GrammarProgress newGrammarProgress = new GrammarProgress(
                        new GrammarProgressId(userId, grammar.getId(), courseId),
                        false,
                        is_finish_quiz,
                        LocalDateTime.now(),
                        false,
                        user,
                        grammar,
                        course);
                grammarProgressMapper.insertGrammarProgress(newGrammarProgress);
            }
        }

        return grammarProgressMapper.getGrammarProgressByUserIdAndCourseId(userId, courseId);
    }

    public void markGrammarTheoryAsLearned(Integer userId, Integer grammarId, Integer courseId) {
        if (grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammarId, courseId)) {
            grammarProgressMapper.markGrammarTheoryAsLearned(userId, grammarId, courseId);
        } else {
            throw new RuntimeException(
                    "Grammar progress not found for user ID: " + userId + " and grammar ID: " + grammarId);
        }
    }

    public void markGrammarQuizAsFinished(Integer userId, Integer grammarId, Integer courseId) {
        if (grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammarId, courseId)) {
            grammarProgressMapper.markGrammarQuizAsFinished(userId, grammarId, courseId);
        } else {
            throw new RuntimeException(
                    "Grammar progress not found for user ID: " + userId + " and grammar ID: " + grammarId);
        }
    }

    public int countLearnedGrammar(Integer userId, Integer courseId) {
        return grammarProgressMapper.countLearnedGrammar(userId, courseId);
    }

    public int countNotLearnedGrammar(Integer userId, Integer courseId) {
        return grammarProgressMapper.countNotLearnedGrammar(userId, courseId);
    }
}
