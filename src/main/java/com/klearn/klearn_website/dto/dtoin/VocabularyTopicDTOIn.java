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
public class VocabularyTopicDTOIn {

    @NotBlank(message = "Topic name cannot be blank")
    @Size(max = 50, message = "Topic name must be at most 50 characters")
    private String topic_name;

    @Size(max = 2000, message = "Topic description must be at most 2000 characters")
    private String topic_description;

    @Size(max = 1000, message = "Topic image URL must be at most 1000 characters")
    private String topic_image;

    @NotNull(message = "Course ID cannot be null")
    @Positive(message = "Course ID must be a positive value")
    private Integer course_id;
}
