package com.klearn.klearn_website.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

    @NotNull(message = "Learned status cannot be null")
    @Column(name = "is_learned", nullable = false)
    private Boolean is_learned = false;

    @NotNull(message = "Proficient status cannot be null")
    @Column(name = "is_proficient", nullable = false)
    private Boolean is_proficient = false;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;

    @NotNull(message = "Deleted status cannot be null")
    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")  // Maps the user_id from the embedded ID
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @NotNull(message = "Vocabulary cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vocabulary_id")  // Maps the vocabulary_id from the embedded ID
    @JoinColumn(name = "vocabulary_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Vocabulary vocabulary;

    @NotNull(message = "Vocabulary topic cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("topic_id")  // Maps the topic_id from the embedded ID
    @JoinColumn(name = "topic_id", referencedColumnName = "id", insertable = false, updatable = false)
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

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VocabularyProgressId implements Serializable {

        @NotNull(message = "User ID cannot be null")
        @Column(name = "user_id")
        private Integer user_id;

        @NotNull(message = "Vocabulary ID cannot be null")
        @Column(name = "vocabulary_id")
        private Integer vocabulary_id;

        @NotNull(message = "Topic ID cannot be null")
        @Column(name = "topic_id")
        private Integer topic_id;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
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
