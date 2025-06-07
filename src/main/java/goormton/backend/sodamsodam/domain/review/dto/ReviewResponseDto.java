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
@Schema(description = "리뷰 응답 DTO")
public class ReviewResponseDto {
    @Schema(description = "리뷰 ID")
    private Long reviewId;

    @Schema(description = "작성자 이름")
    private String username;

    @Schema(description = "리뷰 본문 내용", example = "맛있어요.")
    private String content;

    @Schema(description = "리뷰 태그 목록")
    private List<ReviewTag> tags;

    @Schema(description = "이미지 정보 목록")
    private List<ImageInfoDto> images;

    @Schema(description = "작성일시", example = "2025-05-29T15:00:00")
    private LocalDateTime createdAt;

    public static ReviewResponseDto from(Review review, String username, List<ImageInfoDto> images) {
        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .username(username)
                .content(review.getContent())
                .tags(extractTags(review))
                .images(images)
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
