package com.klearn.klearn_website.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grammar_progress")
public class GrammarProgress {

    @EmbeddedId
    private GrammarProgressId id;

    @Column(name = "is_learned_theory", nullable = false)
    private Boolean is_learned_theory;

    @Column(name = "is_finish_quiz", nullable = false)
    private Boolean is_finish_quiz;

    @Column(name = "last_modified")
    private LocalDateTime last_modified;

    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grammar_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Grammar grammar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Course course;

    @Embeddable
    public static class GrammarProgressId implements Serializable {
        private Integer user_id;
        private Integer grammar_id;
        private Integer course_id;

        public GrammarProgressId() {
        }

        public GrammarProgressId(Integer user_id, Integer grammar_id, Integer course_id) {
            this.user_id = user_id;
            this.grammar_id = grammar_id;
            this.course_id = course_id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            GrammarProgressId that = (GrammarProgressId) o;
            return Objects.equals(user_id, that.user_id) &&
                    Objects.equals(grammar_id, that.grammar_id) &&
                    Objects.equals(course_id, that.course_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user_id, grammar_id, course_id);
        }
    }
}
