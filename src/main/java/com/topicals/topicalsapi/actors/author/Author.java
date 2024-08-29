package com.topicals.topicalsapi.actors.author;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topicals.topicalsapi.actors.appuser.Appuser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "author")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Author extends Appuser {

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime authorCreatedAt;

    @Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime authorUpdatedAt;

    public Author(String bio) {
        this.bio = bio;
    }
}
