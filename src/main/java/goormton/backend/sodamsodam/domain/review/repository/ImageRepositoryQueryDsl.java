package goormton.backend.sodamsodam.domain.review.repository;

import java.util.List;

public interface ImageRepositoryQueryDsl {
    List<String> findTop3UrlsByPlaceId(String placeId);
}
