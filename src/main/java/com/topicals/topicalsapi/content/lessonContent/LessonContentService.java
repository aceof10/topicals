package com.topicals.topicalsapi.content.lessonContent;

import com.topicals.topicalsapi.actors.appuser.IUserRepository;
import com.topicals.topicalsapi.actors.author.Author;
import com.topicals.topicalsapi.content.lesson.Lesson;
import com.topicals.topicalsapi.content.lesson.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonContentService {

    private final LessonContentRepository lessonContentRepository;
    private final IUserRepository userRepository;
    private final LessonRepository lessonRepository;

    public LessonContentService(
            LessonContentRepository lessonContentRepository,
            IUserRepository userRepository, LessonRepository lessonRepository
    ) {
        this.lessonContentRepository = lessonContentRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<LessonContent> getAllLessonContents() {
        return lessonContentRepository.findAll();
    }

    public Optional<LessonContent> getLessonContentById(UUID id) {
        return lessonContentRepository.findById(id);
    }

//    @Transactional

    public LessonContent createLessonContent(UUID lessonId, LessonContent lessonContent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Author author = (Author) userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        lessonContent.setAuthor(author);

        Lesson lesson = lessonRepository.findById(lessonId)
                        .orElseThrow(() -> new IllegalArgumentException("Lesson does not exist"));
        lessonContent.setLesson(lesson);

        lessonContentRepository.save(lessonContent);

        return lessonContentRepository.findById(lessonContent.getContentId())
                .orElseThrow(() -> new EntityNotFoundException("LessonContent not found after save"));
    }

    @Transactional
    public LessonContent updateLessonContent(UUID id, LessonContent lessonContent) {
        return lessonContentRepository.findById(id)
                .map(existingContent -> {
                    existingContent.setContent(lessonContent.getContent());
                    existingContent.setUpdatedAt(lessonContent.getUpdatedAt());
                    return lessonContentRepository.save(existingContent);
                })
                .orElseThrow(() -> new RuntimeException("LessonContent not found"));
    }

    @Transactional
    public void deleteLessonContent(UUID id) {
        lessonContentRepository.deleteById(id);
    }
}

