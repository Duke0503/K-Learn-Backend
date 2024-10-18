package com.klearn.klearn_website.dto.dtoin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyTopicDTOIn {

    private String topic_name;

    private String topic_description;

    private String topic_image;

    private Integer course_id;
}