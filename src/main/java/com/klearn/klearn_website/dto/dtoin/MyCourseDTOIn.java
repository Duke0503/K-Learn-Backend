package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyCourseDTOIn {

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be a positive value")
    private Integer user_id;

    @NotNull(message = "Course ID cannot be null")
    @Positive(message = "Course ID must be a positive value")
    private Integer course_id;

    @NotNull(message = "Payment status cannot be null")
    private String payment_status;
}
