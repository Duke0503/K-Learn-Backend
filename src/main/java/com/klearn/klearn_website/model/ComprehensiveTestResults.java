package com.klearn.klearn_website.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comprehensive_test_results")
public class ComprehensiveTestResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Test type cannot be blank")
    @Column(name = "test_type", nullable = false)
    private String test_type;

    @NotNull(message = "Test date cannot be null")
    @Column(name = "test_date", nullable = false)
    private LocalDateTime test_date;

    @NotNull(message = "Number of correct questions cannot be null")
    @Min(value = 0, message = "Number of correct questions cannot be negative")
    @Column(name = "no_correct_questions", nullable = false)
    private Integer no_correct_questions;

    @NotNull(message = "Number of incorrect questions cannot be null")
    @Min(value = 0, message = "Number of incorrect questions cannot be negative")
    @Column(name = "no_incorrect_questions", nullable = false)
    private Integer no_incorrect_questions;

    @NotNull(message = "Duration cannot be null")
    @Min(value = 0, message = "Duration must be a positive value")
    @Column(name = "duration", nullable = false)
    private Integer duration;  // Duration in seconds

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;

    @NotNull(message = "Deleted status cannot be null")
    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotNull(message = "Course cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    // Lifecycle callbacks for setting timestamps
    @PrePersist
    protected void onCreate() {
        this.last_modified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.last_modified = LocalDateTime.now();
    }
}
