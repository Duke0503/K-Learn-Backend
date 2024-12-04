package com.klearn.klearn_website.dto.dtoin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VocabularyQuizAnswerDTOIn {
    private String user_answer;

    @NotNull(message = "is_correct field cannot be null")
    private Boolean is_correct;

    private String type;

    private String word;

    private String definition;

    private String[] options;
}
