package com.klearn.klearn_website.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="courses")
public class Course {
  
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "course_name", nullable = false, length = 255)
  private String course_name;

  @Column(name = "course_level", nullable = false, length = 50)
  private String course_level;

  @Column(name = "course_description", columnDefinition = "TEXT")
  private String course_description;

  @Column(name = "course_image", length = 255)
  private String course_image;

  @Column(name = "course_price", precision = 18, scale = 2)
  private BigDecimal course_price;

  @Column(name = "created_at")
  private LocalDateTime created_at;

  @Column(name = "last_modified")
  private LocalDateTime last_modified;

  @Column(name = "is_deleted", columnDefinition = "BIT DEFAULT 0")
  private Boolean is_deleted;
}
