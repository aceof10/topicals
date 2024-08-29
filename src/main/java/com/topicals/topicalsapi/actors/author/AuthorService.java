package com.topicals.topicalsapi.actors.author;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }
}
