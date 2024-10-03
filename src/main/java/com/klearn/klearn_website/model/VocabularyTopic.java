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
@Table(name = "vocabulary_topic")
public class VocabularyTopic {
  
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "topic_name", nullable = false, length = 50)
  private String topic_name;

  @Column(name = "topic_description", columnDefinition = "TEXT")
  private String topic_description;

  @Column(name = "topic_image", columnDefinition = "TEXT")
  private String topic_image;

  @Column(name = "created_at")
  private LocalDateTime created_at;

  @Column(name = "last_modified")
  private LocalDateTime last_modified;

  @Column(name = "is_deleted", columnDefinition = "BIT DEFAULT 0")
  private Boolean is_deleted;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
  private Course course; 
}
