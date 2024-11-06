package com.klearn.klearn_website.service.quiz;

import com.klearn.klearn_website.mapper.ComprehensiveTestResultsMapper;
import com.klearn.klearn_website.model.ComprehensiveTestResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComprehensiveTestResultsService {

    private final ComprehensiveTestResultsMapper comprehensiveTestResultsMapper;

    public ComprehensiveTestResults addComprehensiveTestResult(ComprehensiveTestResults comprehensiveTestResults) {
        return comprehensiveTestResultsMapper.insertComprehensiveTestResults(comprehensiveTestResults);
    }

    public ComprehensiveTestResults getMostRecentTestByCourseUserAndType(Integer courseId, Integer userId, String testType) {
        return comprehensiveTestResultsMapper.findMostRecentTestByCourseUserAndType(courseId, userId, testType);
    }
}