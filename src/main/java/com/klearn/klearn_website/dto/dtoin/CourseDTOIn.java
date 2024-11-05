package com.klearn.klearn_website.dto.dtoin;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Data
public class CourseDTOIn {

    @NotNull(message = "Course name cannot be null")
    @Size(min = 1, max = 255, message = "Course name must be between 1 and 255 characters")
    private String course_name;

    @NotNull(message = "Course level cannot be null")
    @Size(min = 1, max = 50, message = "Course level must be between 1 and 50 characters")
    private String course_level;

    @Size(max = 2000, message = "Course description must be at most 2000 characters")
    private String course_description;

    @Size(max = 255, message = "Course image URL must be at most 255 characters")
    private String course_image;

    @NotNull(message = "Course price cannot be null")
    @PositiveOrZero(message = "Course price must be zero or a positive value")
    private BigDecimal course_price;
}
