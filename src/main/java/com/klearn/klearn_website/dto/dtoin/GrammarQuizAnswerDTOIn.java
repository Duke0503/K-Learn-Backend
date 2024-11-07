package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GrammarQuizAnswerDTOIn {

    @NotNull(message = "User answer cannot be null")
    @Size(min = 1, message = "User answer cannot be empty")
    private String user_answer;

    @NotNull(message = "is_correct field cannot be null")
    private Boolean is_correct;
    
    @NotNull(message = "Question ID cannot be null")
    private Integer question_id;
    
    private String[] options;
    
    @NotNull(message = "Grammar ID cannot be null")
    private Integer grammar_id;
}
