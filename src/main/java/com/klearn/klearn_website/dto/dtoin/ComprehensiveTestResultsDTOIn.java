package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComprehensiveTestResultsDTOIn {

    @NotBlank(message = "Test type cannot be blank")
    private String test_type;

    @NotNull(message = "Number of correct questions cannot be null")
    @Min(value = 0, message = "Number of correct questions cannot be negative")
    private Integer no_correct_questions;

    @NotNull(message = "Number of incorrect questions cannot be null")
    @Min(value = 0, message = "Number of incorrect questions cannot be negative")
    private Integer no_incorrect_questions;

    @NotNull(message = "Duration cannot be null")
    @Positive(message = "Duration must be a positive value")
    private Integer duration;

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be greater than or equal to 1")
    private Integer user_id;

    @NotNull(message = "Course ID cannot be null")
    @Min(value = 1, message = "Course ID must be greater than or equal to 1")
    private Integer course_id;
}
