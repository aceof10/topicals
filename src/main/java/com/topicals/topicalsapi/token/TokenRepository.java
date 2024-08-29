package com.topicals.topicalsapi.token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query(value = """
      select t from Token t inner join Appuser u\s
      on t.user.userId = u.userId\s
      where u.userId = :userId and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(UUID userId);

    Optional<Token> findByToken(String token);
}
