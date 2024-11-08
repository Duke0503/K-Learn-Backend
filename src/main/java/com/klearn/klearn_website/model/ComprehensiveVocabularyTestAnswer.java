package com.klearn.klearn_website.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
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
@Table(name = "comprehensive_vocabulary_test_answer")
public class ComprehensiveVocabularyTestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_answer")
    private String user_answer;

    @Column(name = "word")
    private String word;

    @Column(name = "type")
    private String type;

    @Column(name = "definition")
    private String definition;

    @Column(name = "options")
    private String options;

    @NotNull(message = "Last modified date cannot be null")
    @Column(name = "last_modified")
    private LocalDateTime last_modified;

    @NotNull(message = "is_deleted field cannot be null")
    @Column(name = "is_deleted")
    private Boolean is_deleted = false;

    @NotNull(message = "is_correct field cannot be null")
    @Column(name = "is_correct")
    private Boolean is_correct = false;

    @NotNull(message = "Comprehensive test results cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprehensive_test_results_id", referencedColumnName = "id", nullable = false)
    private ComprehensiveTestResults comprehensiveTestResults;
}
