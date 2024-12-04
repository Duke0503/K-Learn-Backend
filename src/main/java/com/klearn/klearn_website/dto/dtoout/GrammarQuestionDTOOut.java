package com.klearn.klearn_website.dto.dtoout;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GrammarQuestionDTOOut {
    private Integer question_id;

    private String type;

    private String question_text;

    private String correct_answer;

    private List<String> options;

    private Integer grammar_id;

    private Integer grammar_lesson_number;

    private String grammar_name;
}
