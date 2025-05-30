package goormton.backend.sodamsodam.domain.review.entity;

import goormton.backend.sodamsodam.domain.common.BaseEntity;
import goormton.backend.sodamsodam.domain.review.enums.ReviewTag;
import goormton.backend.sodamsodam.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class Review extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "place_id", length = 10, nullable = false)
    private String placeId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private ReviewTag tag1;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private ReviewTag tag2;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private ReviewTag tag3;

    public void update(String content, ReviewTag tag1, ReviewTag tag2, ReviewTag tag3) {
        this.content = content;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
    }
}
