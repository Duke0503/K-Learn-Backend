package com.klearn.klearn_website.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;

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
@Table(name = "grammar")
public class Grammar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Grammar name cannot be blank")
    @Size(max = 255, message = "Grammar name must be at most 255 characters")
    @Column(name = "grammar_name", nullable = false, length = 255)
    private String grammar_name;

    @Size(max = 5000, message = "Grammar description must be at most 5000 characters")
    @Column(name = "grammar_description", columnDefinition = "TEXT")
    private String grammar_description;

    @Size(max = 5000, message = "Explanation must be at most 5000 characters")
    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @Size(max = 5000, message = "Example must be at most 5000 characters")
    @Column(name = "example", columnDefinition = "TEXT")
    private String example;

    @NotNull(message = "Lesson number cannot be null")
    @PositiveOrZero(message = "Lesson number must be zero or a positive value")
    @Column(name = "lesson_number")
    private Integer lesson_number;

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
        this.last_modified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.last_modified = LocalDateTime.now();
    }
}
