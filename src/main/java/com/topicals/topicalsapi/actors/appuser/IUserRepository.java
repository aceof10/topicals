package com.topicals.topicalsapi.actors.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<Appuser, UUID> {

    Optional<Appuser> findUserByUserId(UUID userId);
    Optional<Appuser> findUserByEmail(String email);
    boolean existsByEmail(String email);
}
