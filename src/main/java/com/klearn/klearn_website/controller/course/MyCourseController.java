package com.klearn.klearn_website.controller.course;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klearn.klearn_website.dto.dtoin.MyCourseDTOIn;
import com.klearn.klearn_website.model.MyCourse;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.course.MyCourseService;
import com.klearn.klearn_website.service.user.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/api/mycourse")
public class MyCourseController {
  private MyCourseService myCourseService;
  private UserService userService;

  @GetMapping("/user/courses")
  public ResponseEntity<String> getUserCourses() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();
      User user = userService.getUser(username);
      String courses = myCourseService.getMyCourseByUserId(user.getId());
      return ResponseEntity.ok(courses);
  }

  @GetMapping("/vocabulary/{courseId}/progress")
  public ResponseEntity<String> getCourseProgress (@PathVariable Integer courseId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    String username = authentication.getName();

    User user = userService.getUser(username);
    
    String progress = myCourseService.getVocabularyProgressByUserIdAndCourseId(user.getId(), courseId);

    return ResponseEntity.ok(progress);
  }

  @GetMapping("/grammar/{courseId}/progress")
  public ResponseEntity<String> getGrammarProgress(@PathVariable Integer courseId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.getUser(username);
    String progress = myCourseService.getGrammarProgressByUserIdAndCourseId(user.getId(), courseId);
    return ResponseEntity.ok(progress);
  }

  @PostMapping("/enroll")
  public ResponseEntity<String> enrollInCourse(@RequestBody MyCourseDTOIn myCourseDTOIn) {
      myCourseService.insertMyCourse(myCourseDTOIn);
      return ResponseEntity.ok("Course enrolled successfully");
  }
}
