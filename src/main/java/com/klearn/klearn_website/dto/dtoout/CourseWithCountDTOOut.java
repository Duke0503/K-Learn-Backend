package com.klearn.klearn_website.dto.dtoout;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CourseWithCountDTOOut {
    private Integer course_id;

    private String course_name;

    private String course_level;

    private String course_description;

    private String course_image;

    private BigDecimal course_price;

    private Long user_count; 
}
