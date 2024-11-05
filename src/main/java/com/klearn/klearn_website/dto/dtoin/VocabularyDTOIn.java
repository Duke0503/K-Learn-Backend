package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyDTOIn {

    @NotBlank(message = "Word cannot be blank")
    @Size(max = 50, message = "Word must be at most 50 characters")
    private String word;

    @NotBlank(message = "Definition cannot be blank")
    @Size(max = 2000, message = "Definition must be at most 2000 characters")
    private String definition;

    @Size(max = 1000, message = "Transcription must be at most 1000 characters")
    private String transcription;

    @Size(max = 1000, message = "Image URL must be at most 1000 characters")
    private String image;

    @NotNull(message = "Topic ID cannot be null")
    @Positive(message = "Topic ID must be a positive value")
    private Integer topic_id;

}
