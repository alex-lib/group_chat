package org.my.repositories;
import org.my.models.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Integer> {
    Optional<ChatUser> findBySessionId(String sessionId);
}