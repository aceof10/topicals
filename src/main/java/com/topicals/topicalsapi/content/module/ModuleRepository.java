package com.topicals.topicalsapi.content.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {
    @Query(value = """
        SELECT s FROM Module s INNER JOIN Course f\s
        on s.course.courseId = f.courseId\s
    """)
    List<Module> findAllWithModule();
}
