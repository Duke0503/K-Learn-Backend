package com.klearn.klearn_website.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grammar")
public class Grammar {
  
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "grammar_name", nullable = false, length = 255)
  private String grammar_name;

  @Column(name = "explanation", columnDefinition = "TEXT")
  private String explanation;

  @Column(name = "example", columnDefinition = "TEXT")
  private String example;

  @Column(name = "lesson_number")
  private Integer lesson_number;

  @Column(name = "last_modified")
  private LocalDateTime last_modified;

  @Column(name = "is_deleted")
  private Boolean is_deleted;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
  private Course course; 
}
