package com.klearn.klearn_website.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Course name is required")
    @Size(max = 255, message = "Course name must be less than 255 characters")
    @Column(name = "course_name", nullable = false, length = 255)
    private String course_name;

    @NotBlank(message = "Course level is required")
    @Size(max = 50, message = "Course level must be less than 50 characters")
    @Column(name = "course_level", nullable = false, length = 50)
    private String course_level;

    @Column(name = "course_description", columnDefinition = "TEXT")
    private String course_description;

    @Size(max = 255, message = "Course image URL must be less than 255 characters")
    @Column(name = "course_image", length = 255)
    private String course_image;

    @NotNull(message = "Course price is required")
    @DecimalMin(value = "0", inclusive = false, message = "Course price must be greater than 0")
    @Digits(integer = 18, fraction = 0, message = "Course price must be a valid whole number")
    @Column(name = "course_price", precision = 18, scale = 0)
    private BigDecimal course_price;

    @PastOrPresent(message = "Creation date cannot be in the future")
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @PastOrPresent(message = "Last modified date cannot be in the future")
    @Column(name = "last_modified")
    private LocalDateTime last_modified;

    @NotNull(message = "Deleted status is required")
    @Column(name = "is_deleted")
    private Boolean is_deleted;
}
