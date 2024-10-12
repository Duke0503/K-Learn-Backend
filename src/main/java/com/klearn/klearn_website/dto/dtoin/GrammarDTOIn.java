package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class GrammarDTOIn {
  private String grammar_name;

  private String grammar_description;
  
  private String explanation;

  private String example;

  private Integer lesson_number;
  
  private Integer course_id; 
}
