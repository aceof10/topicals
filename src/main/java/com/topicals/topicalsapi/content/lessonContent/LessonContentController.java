package com.topicals.topicalsapi.content.lessonContent;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lessons/content")
public class LessonContentController {

    private final LessonContentService lessonContentService;

    public LessonContentController(LessonContentService lessonContentService) {
        this.lessonContentService = lessonContentService;
    }

    @GetMapping
    public ResponseEntity<List<LessonContent>> getAllLessonContents() {
        List<LessonContent> contents = lessonContentService.getAllLessonContents();
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonContent> getLessonContentById(@PathVariable UUID id) {
        return lessonContentService.getLessonContentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LessonContent> createLessonContent(
            @PathVariable("lessonId") UUID lessonId,
            @RequestBody @Valid LessonContent lessonContent
    ) {
        LessonContent createdContent = lessonContentService.createLessonContent(lessonId, lessonContent);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonContent> updateLessonContent(@PathVariable UUID id, @RequestBody LessonContent lessonContent) {
        LessonContent updatedContent = lessonContentService.updateLessonContent(id, lessonContent);
        return ResponseEntity.ok(updatedContent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLessonContent(@PathVariable UUID id) {
        lessonContentService.deleteLessonContent(id);
        return ResponseEntity.noContent().build();
    }
}
