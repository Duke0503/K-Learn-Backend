package com.klearn.klearn_website.dto.course;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateCourseDto {
  String course_name;

  String course_level;

  String course_description;

  BigDecimal course_price;
}
