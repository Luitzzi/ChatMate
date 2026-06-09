package de.luisgerlinger.chatserver.repository;

import de.luisgerlinger.chatserver.entity.UserBE;
import jakarta.annotation.Nullable;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserBE, UUID> {
    Optional<UserBE> getUserById(UUID id);

    Optional<UserBE> getUserByUsername(String username);

    List<UserBE> findAll();

    boolean existsById(@NonNull UUID userId);
}
