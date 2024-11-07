package com.klearn.klearn_website.mapper;

import org.apache.ibatis.annotations.*;

import com.klearn.klearn_website.model.ComprehensiveTestResults;

@Mapper
public interface ComprehensiveTestResultsMapper {

    // Insert a new comprehensive test result and return the inserted result
    @Insert("INSERT INTO comprehensive_test_results (test_type, test_date, no_correct_questions, no_incorrect_questions, last_modified, is_deleted, user_id, course_id) "
            + "VALUES (#{test_type}, #{test_date}, #{no_correct_questions}, #{no_incorrect_questions}, #{last_modified}, #{is_deleted}, #{user.id}, #{course.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertComprehensiveTestResults(ComprehensiveTestResults comprehensiveTestResults);


    // Get the most recent comprehensive test result by course_id, user_id, and
    // test_type, including course and user data
    @Select("SELECT ctr.*, c.*, u.* FROM comprehensive_test_results ctr " +
            "JOIN courses c ON ctr.course_id = c.id " +
            "JOIN users u ON ctr.user_id = u.id " +
            "WHERE ctr.course_id = #{courseId} AND ctr.user_id = #{userId} AND ctr.test_type = #{testType} AND ctr.is_deleted = false "
            +
            "ORDER BY ctr.test_date DESC LIMIT 1")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "test_type", column = "test_type"),
            @Result(property = "test_date", column = "test_date"),
            @Result(property = "no_correct_questions", column = "no_correct_questions"),
            @Result(property = "no_incorrect_questions", column = "no_incorrect_questions"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),

            // Nested mapping for user
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.username", column = "username"),
            @Result(property = "user.email", column = "email"),
            @Result(property = "user.fullname", column = "fullname"),
            @Result(property = "user.dob", column = "dob"),
            @Result(property = "user.avatar", column = "avatar"),
            @Result(property = "user.gender", column = "gender"),
            @Result(property = "user.last_login", column = "last_login"),
            @Result(property = "user.last_modified", column = "user_last_modified"),
            @Result(property = "user.is_deleted", column = "user_is_deleted"),

            // Nested mapping for course
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_description", column = "course_description"),
            @Result(property = "course.course_image", column = "course_image"),
            @Result(property = "course.course_price", column = "course_price"),
            @Result(property = "course.created_at", column = "created_at"),
            @Result(property = "course.last_modified", column = "course_last_modified"),
            @Result(property = "course.is_deleted", column = "course_is_deleted")
    })
    ComprehensiveTestResults findMostRecentTestByCourseUserAndType(@Param("courseId") Integer courseId,
            @Param("userId") Integer userId,
            @Param("testType") String testType);
}
