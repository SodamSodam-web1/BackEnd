package goormton.backend.sodamsodam.domain.user.domain;

import goormton.backend.sodamsodam.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @JoinColumn(name = "nickname")
    private String name;

    @JoinColumn(name = "profilePicture")
    private String profile_picture;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String provider;
    private String providerId;

    @Builder(toBuilder = true)
    User(String username, String email, String password, String name, String profile_picture, UserRole role, String provider, String providerId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profile_picture = profile_picture;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
