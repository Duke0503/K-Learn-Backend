package com.klearn.klearn_website.service.grammar;

import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.Grammar;
import com.klearn.klearn_website.dto.grammar.CreateGrammarDto;
import com.klearn.klearn_website.mapper.CourseMapper;
import com.klearn.klearn_website.mapper.GrammarMapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GrammarService {
  private CourseMapper courseMapper;
  private GrammarMapper grammarMapper;

  public void createGrammar(CreateGrammarDto createGrammarDto) {
    Grammar grammar = new Grammar();

    grammar.setGrammar_name(createGrammarDto.getGrammar_name());
    grammar.setGrammar_description(createGrammarDto.getGrammar_description());
    grammar.setExplanation(createGrammarDto.getExplanation());
    grammar.setExample(createGrammarDto.getExample());
    grammar.setLesson_number(createGrammarDto.getLesson_number());
    grammar.setIs_deleted(false);
    grammar.setLast_modified(LocalDateTime.now());

    Course course = courseMapper.findCourseById(createGrammarDto.getCourse_id());
    if (course == null) {
      throw new RuntimeException("Course not found with ID: " + createGrammarDto.getCourse_id());
    }

    grammar.setCourse(course);

    grammarMapper.insertGrammar(grammar);
  }

  public List<Grammar> getAllGrammar() {
    return grammarMapper.getAllGrammar();
  }

  public List<Grammar> getGrammarByCourseId(Integer courseId) {
    return grammarMapper.getAllByCourseId(courseId);
  }
}
