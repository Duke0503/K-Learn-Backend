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
@Table(name = "vocabulary_topic")
public class VocabularyTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Topic name cannot be blank")
    @Size(max = 50, message = "Topic name must be at most 50 characters")
    @Column(name = "topic_name", nullable = false, length = 50)
    private String topic_name;

    @Size(max = 500, message = "Topic description must be at most 500 characters")
    @Column(name = "topic_description", columnDefinition = "TEXT")
    private String topic_description;

    @Size(max = 1000, message = "Topic image URL must be at most 1000 characters")
    @Column(name = "topic_image", columnDefinition = "TEXT")
    private String topic_image;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;

    @NotNull(message = "Deleted status cannot be null")
    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @NotNull(message = "Course cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    // Lifecycle callbacks for setting the timestamps
    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.last_modified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.last_modified = LocalDateTime.now();
    }
}
