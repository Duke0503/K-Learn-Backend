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
public class VocabularyQuizSubmissionDTOIn {
    @NotNull(message = "Answers cannot be null")
    private List<VocabularyQuizAnswerDTOIn> answers;

    @NotNull(message = "Course ID cannot be null")
    private Integer course_id;
}
