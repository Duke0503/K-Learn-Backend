package com.klearn.klearn_website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VocabularyQuestion {
  private Integer vocabulary_id;

  private String type;

  private String word;

  private String definition;

  private List<String> options;
}