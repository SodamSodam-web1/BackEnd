package goormton.backend.sodamsodam.domain.review.dto;

import goormton.backend.sodamsodam.domain.review.entity.Review;
import goormton.backend.sodamsodam.domain.review.enums.ReviewTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "내가 작성한 리뷰 목록 항목 DTO")
public class MyReviewDto {
    @Schema(description = "리뷰 ID")
    private Long reviewId;

    @Schema(description = "장소 ID")
    private String placeId;

    @Schema(description = "리뷰 본문 내용", example = "맛있어요.")
    private String content;

    @Schema(description = "리뷰 태그 목록")
    private List<ReviewTag> tags;

    @Schema(description = "이미지 URL 목록")
    private List<String> imageUrls;

    @Schema(description = "작성일시", example = "2025-05-29T15:00:00")
    private LocalDateTime createdAt;

    public static MyReviewDto from(Review review, List<String> imageUrls) {
        return MyReviewDto.builder()
                .reviewId(review.getId())
                .placeId(review.getPlaceId())
                .content(review.getContent())
                .tags(extractTags(review))
                .imageUrls(imageUrls)
                .createdAt(review.getCreatedAt())
                .build();
    }

    private static List<ReviewTag> extractTags(Review review) {
        List<ReviewTag> tags = new ArrayList<>();
        if (review.getTag1() != null) tags.add(review.getTag1());
        if (review.getTag2() != null) tags.add(review.getTag2());
        if (review.getTag3() != null) tags.add(review.getTag3());
        return tags;
    }
}
