package com.klearn.klearn_website.service.course;

import com.klearn.klearn_website.dto.dtoin.CourseDTOIn;
import com.klearn.klearn_website.mapper.CourseMapper;
import com.klearn.klearn_website.model.Course;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseMapper courseMapper;

    /**
     * Retrieves all courses that are not deleted.
     *
     * @return List of all available courses.
     */
    public List<Course> getAllCourses() {
        return courseMapper.findAll();
    }

    /**
     * Creates a new course using the provided DTO.
     *
     * @param courseDTOIn The DTO containing the course details to be created.
     * @return The created course entity.
     */
    public Course createCourse(CourseDTOIn courseDTOIn) {
        Course course = new Course();

        // Set fields using the correct setter methods
        course.setCourse_name(courseDTOIn.getCourse_name());
        course.setCourse_level(courseDTOIn.getCourse_level());
        course.setCourse_description(courseDTOIn.getCourse_description());
        course.setCourse_image(courseDTOIn.getCourse_image());
        course.setCourse_price(courseDTOIn.getCourse_price());
        course.setCreated_at(LocalDateTime.now());
        course.setLast_modified(LocalDateTime.now());
        course.setIs_deleted(false);

        courseMapper.createCourse(course);
        return course;
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param courseId The ID of the course to retrieve.
     * @return An Optional containing the course if found, or empty if not.
     */
    public Optional<Course> getCourseById(Integer courseId) {
        return courseMapper.findCourseById(courseId);
    }

    /**
     * Updates an existing course.
     *
     * @param courseId    The ID of the course to update.
     * @param courseDTOIn The DTO containing the updated course details.
     * @return The updated course entity.
     */
    public Course updateCourse(Integer courseId, CourseDTOIn courseDTOIn) {
        Optional<Course> existingCourseOptional = courseMapper.findCourseById(courseId);

        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();

            // Update fields using the correct setter methods
            existingCourse.setCourse_name(courseDTOIn.getCourse_name());
            existingCourse.setCourse_level(courseDTOIn.getCourse_level());
            existingCourse.setCourse_description(courseDTOIn.getCourse_description());
            existingCourse.setCourse_image(courseDTOIn.getCourse_image());
            existingCourse.setCourse_price(courseDTOIn.getCourse_price());
            existingCourse.setLast_modified(LocalDateTime.now());

            courseMapper.updateCourse(existingCourse);
            return existingCourse;
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }

    /**
     * Soft deletes a course by marking it as deleted.
     *
     * @param courseId The ID of the course to delete.
     */
    public void deleteCourse(Integer courseId) {
        Optional<Course> existingCourseOptional = courseMapper.findCourseById(courseId);

        if (existingCourseOptional.isPresent()) {
            courseMapper.softDeleteCourse(courseId);
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }
}
