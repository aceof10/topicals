package com.topicals.topicalsapi.content.lesson;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topicals.topicalsapi.content.lessonContent.LessonContent;
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
public class Lesson {
    
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID lessonId;

    @Column(nullable = false, length = 150)
    @NotNull(message = "lessonName cannot be null")
    @NotBlank(message = "lessonName must not be empty")
    private String lessonName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    @JsonBackReference
    private Module module;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LessonContent> lessonContentList;

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
        Lesson lesson = (Lesson) o;
        return Objects.equals(getLessonId(), lesson.getLessonId())
                && Objects.equals(getLessonName(), lesson.getLessonName())
                && Objects.equals(getModule(), lesson.getModule())
                && Objects.equals(getCreatedAt(), lesson.getCreatedAt())
                && Objects.equals(getUpdatedAt(), lesson.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLessonId(),
                getLessonName(),
                getModule(),
                getCreatedAt(),
                getUpdatedAt());
    }
}
