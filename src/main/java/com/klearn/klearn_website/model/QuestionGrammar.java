package com.klearn.klearn_website.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "question_grammar")
public class QuestionGrammar {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "question_text")
  private String question_text;

  @Column(name = "correct_answer")
  private String correct_answer;

  @Column(name = "incorrect_answer")
  private String incorrect_answer;

  @Column(name = "quiz_type")
  private String quiz_type;

  @Column(name = "created_at")
  private LocalDateTime created_at;

  @Column(name = "last_modified")
  private LocalDateTime last_modified;

  @Column(name = "is_deleted")
  private Boolean is_deleted;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "grammar_id", referencedColumnName = "id", nullable = false)
  private Grammar grammar; 
}  
