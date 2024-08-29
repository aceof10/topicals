package com.topicals.topicalsapi.content.module;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.topicals.topicalsapi.content.course.Course;
import com.topicals.topicalsapi.content.lesson.Lesson;
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
public class Module {
    
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID moduleId;

    @Column(nullable = false, length = 150)
    @NotNull(message = "moduleName cannot be null")
    @NotBlank(message = "moduleName must not be empty")
    private String moduleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Lesson> lessonList;

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
        Module module = (Module) o;
        return Objects.equals(getModuleId(),module.getModuleId())
                && Objects.equals(getModuleName(), module.getModuleName())
                && Objects.equals(getCourse(), module.getCourse())
                && Objects.equals(getLessonList(), module.getLessonList())
                && Objects.equals(getCreatedAt(), module.getCreatedAt())
                && Objects.equals(getUpdatedAt(), module.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModuleId(),
                getModuleName(),
                getCourse(),
                getLessonList(),
                getCreatedAt(),
                getUpdatedAt());
    }
}
