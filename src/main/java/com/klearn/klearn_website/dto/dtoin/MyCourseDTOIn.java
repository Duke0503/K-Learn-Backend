package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MyCourseDTOIn {
  private Integer user_id;

  private Integer course_id;

  private String payment_status;
}
