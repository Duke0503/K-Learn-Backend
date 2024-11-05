package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionGrammarDTOIn {

    @NotBlank(message = "Question text cannot be blank")
    @Size(max = 1000, message = "Question text must be at most 1000 characters")
    private String question_text;

    @NotBlank(message = "Correct answer cannot be blank")
    @Size(max = 255, message = "Correct answer must be at most 255 characters")
    private String correct_answer;

    @NotBlank(message = "Quiz type cannot be blank")
    @Size(max = 50, message = "Quiz type must be at most 50 characters")
    private String quiz_type;

    @Size(min = 1, message = "Incorrect answers list must contain at least one incorrect answer if provided")
    private List<@NotBlank(message = "Incorrect answer cannot be blank") String> incorrect_answer;

    @NotNull(message = "Grammar ID cannot be null")
    @Positive(message = "Grammar ID must be a positive value")
    private Integer grammar_id;

    public boolean isValid() {
        if (!"essay".equalsIgnoreCase(quiz_type) && (incorrect_answer == null || incorrect_answer.isEmpty())) {
            return false;
        }
        return true;
    }
}
