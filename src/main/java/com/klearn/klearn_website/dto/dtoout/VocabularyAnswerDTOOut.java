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

public class VocabularyAnswerDTOOut {
    private Integer answer_id;

    private String user_answer;

    private Boolean is_correct;

    private String type;

    private String word;

    private String definition;

    private List<String> options;
}
