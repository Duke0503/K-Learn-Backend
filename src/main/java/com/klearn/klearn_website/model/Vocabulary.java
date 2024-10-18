package com.klearn.klearn_website.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vocabulary")

public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "word", nullable = false, length = 50)
    private String word;

    @Column(name = "definition", columnDefinition = "TEXT", nullable = false)
    private String definition;

    @Column(name = "transcription", columnDefinition = "TEXT")
    private String transcription;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "last_modified")
    private LocalDateTime last_modified;

    @Column(name = "is_deleted")
    private Boolean is_deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    private VocabularyTopic vocabularyTopic;
}
