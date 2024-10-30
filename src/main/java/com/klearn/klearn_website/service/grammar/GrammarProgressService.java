package com.klearn.klearn_website.service.grammar;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.service.course.CourseService;
import com.klearn.klearn_website.service.user.UserService;
import com.klearn.klearn_website.mapper.GrammarProgressMapper;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.model.GrammarProgress.GrammarProgressId;
import com.klearn.klearn_website.model.QuestionGrammar;
import com.klearn.klearn_website.model.GrammarProgress;
import com.klearn.klearn_website.model.User;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GrammarProgressService {
    private final UserService userService;
    private final GrammarProgressMapper grammarProgressMapper;
    private final CourseService courseService;
    private final GrammarService grammarService;
    private final QuestionGrammarService questionGrammarService;

    // Get GrammarProgress entries for a user and course, and initialize missing
    // records
    public List<GrammarProgress> getGrammarProgressByUserIdAndCourseId(Integer userId, Integer courseId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isEmpty()) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }

        List<Grammar> listGrammar = grammarService.getGrammarByCourseId(courseId);

        for (Grammar grammar : listGrammar) {
            boolean is_finish_quiz = false;
            List<QuestionGrammar> listQuestion = questionGrammarService.getAllQuestionsByGrammarId(grammar.getId());
            if (listQuestion.isEmpty()) {
                is_finish_quiz = true;
            }

            if (!grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammar.getId(), courseId)) {
                GrammarProgress newGrammarProgress = new GrammarProgress(
                        new GrammarProgressId(userId, grammar.getId(), courseId),
                        false,
                        false,
                        false,
                        LocalDateTime.now(),
                        false,
                        user.get(),
                        grammar,
                        course.get());
                grammarProgressMapper.insertGrammarProgress(newGrammarProgress);
            }
        }

        return grammarProgressMapper.getGrammarProgressByUserIdAndCourseId(userId, courseId);
    }

    // Get a specific GrammarProgress entry for a user and grammar ID
    public Optional<GrammarProgress> getGrammarProgressByUserIdAndGrammarId(Integer userId, Integer grammarId) {
        GrammarProgress grammarProgress = grammarProgressMapper.getGrammarProgressByUserIdAndGrammarId(userId,
                grammarId);
        return Optional.ofNullable(grammarProgress);
    }

    // Mark a Grammar entry as learned
    public void markGrammarTheoryAsLearned(Integer userId, Integer grammarId, Integer courseId) {
        if (grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammarId, courseId)) {
            grammarProgressMapper.markGrammarTheoryAsLearned(userId, grammarId, courseId);
        } else {
            throw new RuntimeException(
                    "Grammar progress not found for user ID: " + userId + " and grammar ID: " + grammarId);
        }
    }

    // Mark a Grammar quiz as finished
    public void markGrammarQuizAsFinished(Integer userId, Integer grammarId, Integer courseId) {
        if (grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammarId, courseId)) {
            grammarProgressMapper.markGrammarQuizAsFinished(userId, grammarId, courseId);
        } else {
            throw new RuntimeException(
                    "Grammar progress not found for user ID: " + userId + " and grammar ID: " + grammarId);
        }
    }

    // Mark a Grammar quiz as failed
    public void markGrammarQuizAsFailed(Integer userId, Integer grammarId, Integer courseId) {
        if (grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammarId, courseId)) {
            grammarProgressMapper.markGrammarQuizAsFailed(userId, grammarId, courseId);
        } else {
            throw new RuntimeException(
                    "Grammar progress not found for user ID: " + userId + " and grammar ID: " + grammarId);
        }
    }

    // Count the number of learned Grammar entries for a user and course
    public int countLearnedGrammar(Integer userId, Integer courseId) {
        return grammarProgressMapper.countLearnedGrammar(userId, courseId);
    }

    // Count the number of not learned Grammar entries for a user and course
    public int countNotLearnedGrammar(Integer userId, Integer courseId) {
        return grammarProgressMapper.countNotLearnedGrammar(userId, courseId);
    }

    // Update an existing GrammarProgress entry
    public void updateGrammarProgress(GrammarProgress grammarProgress) {
        if (grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(
                grammarProgress.getId().getUser_id(),
                grammarProgress.getId().getGrammar_id(),
                grammarProgress.getId().getCourse_id())) {
            grammarProgressMapper.updateGrammarProgress(grammarProgress);
        } else {
            throw new RuntimeException("Grammar progress not found for the given IDs.");
        }
    }

    // Soft delete a GrammarProgress entry
    public void softDeleteGrammarProgress(Integer userId, Integer grammarId, Integer courseId) {
        if (grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammarId, courseId)) {
            grammarProgressMapper.softDeleteGrammarProgress(userId, grammarId, courseId);
        } else {
            throw new RuntimeException("Grammar progress not found for the given IDs.");
        }
    }

    // Permanently delete a GrammarProgress entry
    public void deleteGrammarProgressPermanently(Integer userId, Integer grammarId, Integer courseId) {
        if (grammarProgressMapper.existsByUserIdAndGrammarIdAndCourseId(userId, grammarId, courseId)) {
            grammarProgressMapper.deleteGrammarProgressPermanently(userId, grammarId, courseId);
        } else {
            throw new RuntimeException("Grammar progress not found for the given IDs.");
        }
    }

    // Get all active GrammarProgress entries
    public List<GrammarProgress> getAllActiveGrammarProgress() {
        return grammarProgressMapper.getAllActiveGrammarProgress();
    }
}
