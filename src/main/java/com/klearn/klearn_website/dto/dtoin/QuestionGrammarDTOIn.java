package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionGrammarDTOIn {
    private String question_text;

    private String correct_answer;

    private String quiz_type;

    private List<String> incorrect_answer;

    private Integer grammar_id;
}