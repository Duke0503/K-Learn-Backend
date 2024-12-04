package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GrammarDTOIn {

    @NotNull(message = "Grammar name cannot be null")
    @Size(min = 1, max = 255, message = "Grammar name must be between 1 and 255 characters")
    private String grammar_name;

    @NotNull(message = "Grammar description cannot be null")
    @Size(max = 2000, message = "Grammar description must be at most 2000 characters")
    private String grammar_description;

    @Size(max = 2000, message = "Explanation must be at most 2000 characters")
    private String explanation;

    @Size(max = 2000, message = "Example must be at most 2000 characters")
    private String example;

    @NotNull(message = "Lesson number cannot be null")
    @Positive(message = "Lesson number must be a positive value")
    private Integer lesson_number;

    @NotNull(message = "Course ID cannot be null")
    @Positive(message = "Course ID must be a positive value")
    private Integer course_id;
}
