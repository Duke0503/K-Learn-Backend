package com.klearn.klearn_website.service.course;

import com.klearn.klearn_website.dto.dtoin.CourseDTOIn;
import com.klearn.klearn_website.mapper.CourseMapper;
import com.klearn.klearn_website.model.Course;

import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class CourseService {

    private CourseMapper courseMapper;

    public CourseService(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public List<Course> getAllCourses() {
        return courseMapper.findAll();
    }

    public void createCourse(CourseDTOIn courseDTOIn) {
        Course course = new Course();

        course.setCourse_name(courseDTOIn.getCourse_name());
        course.setCourse_level(courseDTOIn.getCourse_level());
        course.setCourse_description(courseDTOIn.getCourse_description());
        course.setCourse_price(courseDTOIn.getCourse_price());
        course.setCreated_at(LocalDateTime.now());
        course.setLast_modified(LocalDateTime.now());
        course.setIs_deleted(false);

        courseMapper.createCourse(course);
    }
}
