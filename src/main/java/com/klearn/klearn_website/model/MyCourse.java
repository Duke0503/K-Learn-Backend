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
@Table(name = "my_course")
public class MyCourse {
  
  @EmbeddedId
  private MyCourseId id;

  @Column(name = "date_registration")
  private LocalDateTime date_registration;

  @Column(name = "payment_status")
  private String payment_status;

  @Column(name = "last_modified")
  private LocalDateTime last_modified;

  @Column(name = "my_progress")
  private Integer my_progress;

  @Column(name = "is_deleted")
  private Boolean is_deleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
  private Course course; 
  
  @Embeddable
  public static class MyCourseId implements Serializable {
    private Integer user_id;
    private Integer course_id;

    public MyCourseId() {}

    public MyCourseId(Integer user_id, Integer course_id) {
      this.user_id = user_id;
      this.course_id = course_id;
    }

    @Override
    public boolean equals(Object o) {
      if(this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
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
