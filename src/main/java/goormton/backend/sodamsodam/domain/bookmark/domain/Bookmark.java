package goormton.backend.sodamsodam.domain.bookmark.domain;

import goormton.backend.sodamsodam.domain.common.BaseEntity;
import goormton.backend.sodamsodam.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "place_id", nullable = false)
    private String placeId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address_name")
    private String addressName;

    @Builder
    public Bookmark(User user, String placeId, String placeName, String phone, String addressName) {
        this.user = user;
        this.placeId = placeId;
        this.placeName = placeName;
        this.phone = phone;
        this.addressName = addressName;
    }
}
