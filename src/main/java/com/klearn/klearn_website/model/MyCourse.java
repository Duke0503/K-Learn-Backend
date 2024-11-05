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
@Table(name = "my_course")
public class MyCourse {

    @EmbeddedId
    private MyCourseId id;

    @NotNull(message = "Date of registration cannot be null")
    @Column(name = "date_registration", nullable = false)
    private LocalDateTime date_registration;

    @NotNull(message = "Payment status cannot be null")
    @Column(name = "payment_status", length = 50, nullable = false)
    private String payment_status;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;

    @NotNull(message = "Deleted status cannot be null")
    @Column(name = "is_deleted", nullable = false)
    private Boolean is_deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Course course;

    // Lifecycle callbacks for setting the timestamps
    @PrePersist
    protected void onCreate() {
        this.last_modified = LocalDateTime.now();
        this.date_registration = LocalDateTime.now();
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
    public static class MyCourseId implements Serializable {

        @NotNull(message = "User ID cannot be null")
        @Column(name = "user_id")
        private Integer user_id;

        @NotNull(message = "Course ID cannot be null")
        @Column(name = "course_id")
        private Integer course_id;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            MyCourseId that = (MyCourseId) o;
            return Objects.equals(user_id, that.user_id) &&
                    Objects.equals(course_id, that.course_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user_id, course_id);
        }
    }
}
