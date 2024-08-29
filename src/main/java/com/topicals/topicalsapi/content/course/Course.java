package com.topicals.topicalsapi.content.course;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.topicals.topicalsapi.content.module.Module;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Course {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID courseId;

    @Column(nullable = false, length = 150)
    @NotNull(message = "courseName cannot be null")
    @NotBlank(message = "courseName must not be empty")
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Module> moduleList;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(getCourseId(), course.getCourseId())
                && Objects.equals(getCourseName(), course.getCourseName())
                && Objects.equals(getModuleList(), course.getModuleList())
                && Objects.equals(getCreatedAt(), course.getCreatedAt())
                && Objects.equals(getUpdatedAt(), course.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(),
                getCourseName(),
                getModuleList(),
                getCreatedAt(),
                getUpdatedAt());
    }
}
