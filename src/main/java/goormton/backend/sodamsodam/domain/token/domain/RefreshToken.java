package goormton.backend.sodamsodam.domain.token.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private String refreshToken;

    @Column(nullable = false)
    private Long userId;

    private LocalDateTime expiryDate;

    @Builder
    public RefreshToken(String refreshToken, Long userId, LocalDateTime expiryDate) {
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }
}
