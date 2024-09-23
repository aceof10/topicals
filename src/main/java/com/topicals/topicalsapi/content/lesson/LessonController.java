package com.topicals.topicalsapi.content.lesson;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/lessons")
public class LessonController {
    
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<Lesson> getAllTopics() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getTopicById(@PathVariable UUID id) {
        return lessonService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lesson> createTopic(@RequestBody Lesson lesson) {
        Lesson savedLesson = lessonService.save(lesson);
        return ResponseEntity.status(201).body(savedLesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateTopic(@PathVariable UUID id, @RequestBody Lesson updatedLesson) {
        return lessonService.findById(id)
                .map(existingTopic -> {
                    existingTopic.setLessonName(updatedLesson.getLessonName());
                    existingTopic.setModule(updatedLesson.getModule());
                    Lesson savedLesson = lessonService.save(existingTopic);
                    return ResponseEntity.ok(savedLesson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable UUID id) {
        if (lessonService.findById(id).isPresent()) {
            lessonService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
