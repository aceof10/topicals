package com.topicals.topicalsapi.actors.author;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topicals.topicalsapi.actors.appuser.Appuser;
import com.topicals.topicalsapi.content.lessonContent.LessonContent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "author")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Author extends Appuser {

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
    @JsonIgnore
    private List<LessonContent> lessonContentList;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime authorCreatedAt;

    @Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime authorUpdatedAt;

    public Author(String bio) {
        this.bio = bio;
    }

    @PrePersist
    protected void onCreate() {
        this.authorCreatedAt = LocalDateTime.now();
        this.authorUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.authorUpdatedAt = LocalDateTime.now();
    }
}
