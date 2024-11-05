package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GrammarProgressDTOIn {

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be a positive value")
    private Integer user_id;

    @NotNull(message = "Grammar ID cannot be null")
    @Positive(message = "Grammar ID must be a positive value")
    private Integer grammar_id;
}
