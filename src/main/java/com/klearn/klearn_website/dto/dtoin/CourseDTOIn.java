package com.klearn.klearn_website.dto.dtoin;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CourseDTOIn {
    String course_name;

    String course_level;

    String course_description;

    BigDecimal course_price;
}
