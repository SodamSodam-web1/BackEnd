package goormton.backend.sodamsodam.domain.bookmark.dto.response;

import goormton.backend.sodamsodam.domain.bookmark.domain.Bookmark;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Schema(description = "유저의 북마크 리스트 조회용 requestDTO입니다.")
public record BookmarkedPlaceResponse(
        String placeId,
        String placeName,
        String addressName,
        String phone,
        LocalDateTime bookmarkedAt
) {
    public static BookmarkedPlaceResponse fromEntity(Bookmark bookmark) {
        return BookmarkedPlaceResponse.builder()
                .placeId(bookmark.getPlaceId())
                .placeName(bookmark.getPlaceName())
                .addressName(bookmark.getAddressName())
                .phone(bookmark.getPhone())
                .bookmarkedAt(bookmark.getCreatedAt())
                .build();
    }
}
