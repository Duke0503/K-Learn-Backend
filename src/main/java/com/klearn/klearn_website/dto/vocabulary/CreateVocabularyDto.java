package com.klearn.klearn_website.dto.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVocabularyDto {

  private String word;

  private String definition;

  private String transcription;

  private String image;

  private Integer topic_id;
  
}
