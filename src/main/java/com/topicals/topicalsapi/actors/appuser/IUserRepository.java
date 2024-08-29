package com.topicals.topicalsapi.actors.appuser;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Appuser, UUID> {

    Optional<Appuser> findUserByUserId(UUID userId);
    Optional<Appuser> findUserByEmail(String email);
    boolean existsByEmail(String email);
}
