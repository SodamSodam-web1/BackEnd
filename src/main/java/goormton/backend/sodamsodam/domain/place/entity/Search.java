package goormton.backend.sodamsodam.domain.place.entity;

import goormton.backend.sodamsodam.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String searchContent;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Search() {
    }

    public Search(String searchContent, User user) {
        this.searchContent = searchContent;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }
}
