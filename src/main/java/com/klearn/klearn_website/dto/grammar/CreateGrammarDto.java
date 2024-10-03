package com.klearn.klearn_website.dto.grammar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CreateGrammarDto {
  private String grammar_name;

  private String explanation;

  private String example;

  private Integer lesson_number;
  
  private Integer course_id; 
}
