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
@Table(name = "vocabulary_progress")
public class VocabularyProgress {

    @EmbeddedId
    private VocabularyProgressId id;

    @Column(name = "is_learned", nullable = false)
    private Boolean is_learned;

    @Column(name = "last_modified", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime last_modified;

    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Vocabulary vocabulary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", insertable = false, updatable = false)
    private VocabularyTopic vocabularyTopic;

    @Embeddable
    public static class VocabularyProgressId implements Serializable {
        private Integer user_id;
        private Integer vocabulary_id;
        private Integer topic_id;

        public VocabularyProgressId() {}

        public VocabularyProgressId(Integer user_id, Integer vocabulary_id, Integer topic_id) {
            this.user_id = user_id;
            this.vocabulary_id = vocabulary_id;
            this.topic_id = topic_id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VocabularyProgressId that = (VocabularyProgressId) o;
            return Objects.equals(user_id, that.user_id) &&
                    Objects.equals(vocabulary_id, that.vocabulary_id) &&
                    Objects.equals(topic_id, that.topic_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user_id, vocabulary_id, topic_id);
        }
    }
}
