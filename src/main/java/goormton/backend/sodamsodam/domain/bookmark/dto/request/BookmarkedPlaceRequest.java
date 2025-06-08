package goormton.backend.sodamsodam.domain.bookmark.dto.request;

public record BookmarkedPlaceRequest(
        String placeId,
        String placeName,
        String addressName,
        String phone
) {
}
