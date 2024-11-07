package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GrammarQuizSubmissionDTOIn {

    @NotNull(message = "Answers cannot be null")
    private List<GrammarQuizAnswerDTOIn> answers;

    @NotNull(message = "Course ID cannot be null")
    private Integer course_id;

    @NotNull(message = "Duration cannot be null")
    private Integer duration;
}
