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
@Table(name = "question_grammar")
public class QuestionGrammar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Question text cannot be blank")
    @Size(max = 1000, message = "Question text must be at most 1000 characters")
    @Column(name = "question_text", nullable = false)
    private String question_text;

    @NotBlank(message = "Correct answer cannot be blank")
    @Size(max = 255, message = "Correct answer must be at most 255 characters")
    @Column(name = "correct_answer", nullable = false)
    private String correct_answer;

    @Size(max = 1000, message = "Incorrect answers must be at most 1000 characters")
    @Column(name = "incorrect_answer")
    private String incorrect_answer;

    @NotBlank(message = "Quiz type cannot be blank")
    @Size(max = 50, message = "Quiz type must be at most 50 characters")
    @Column(name = "quiz_type", nullable = false)
    private String quiz_type;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;

    @NotNull(message = "Deleted status cannot be null")
    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @NotNull(message = "Grammar cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grammar_id", referencedColumnName = "id", nullable = false)
    private Grammar grammar;

    // Lifecycle callbacks for setting timestamps
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
