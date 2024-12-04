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
public class GrammarQuizAnswerDTOIn {

    private String user_answer;

    @NotNull(message = "is_correct field cannot be null")
    private Boolean is_correct;
    
    @NotNull(message = "Question ID cannot be null")
    private Integer question_id;
    
    private String[] options;
    
    @NotNull(message = "Grammar ID cannot be null")
    private Integer grammar_id;
}
