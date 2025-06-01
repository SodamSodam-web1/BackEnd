package goormton.backend.sodamsodam.domain.token.domain.repository;

import goormton.backend.sodamsodam.domain.token.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserId(Long userId);
}
