package com.klearn.klearn_website.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank(message = "Word cannot be blank")
    @Size(max = 50, message = "Word must be at most 50 characters")
    @Column(name = "word", nullable = false, length = 50)
    private String word;

    @NotBlank(message = "Definition cannot be blank")
    @Column(name = "definition", columnDefinition = "TEXT", nullable = false)
    private String definition;

    @Size(max = 1000, message = "Transcription must be at most 1000 characters")
    @Column(name = "transcription", columnDefinition = "TEXT")
    private String transcription;

    @Size(max = 1000, message = "Image URL must be at most 1000 characters")
    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;

    @NotNull(message = "Deleted status cannot be null")
    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @NotNull(message = "Vocabulary topic cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    private VocabularyTopic vocabularyTopic;

    // Lifecycle callbacks for setting the timestamps
    @PrePersist
    protected void onCreate() {
        this.last_modified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.last_modified = LocalDateTime.now();
    }
}
