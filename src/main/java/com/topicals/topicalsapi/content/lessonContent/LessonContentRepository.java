package com.topicals.topicalsapi.content.lessonContent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonContentRepository extends JpaRepository<LessonContent, UUID> {
}
