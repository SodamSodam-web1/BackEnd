package goormton.backend.sodamsodam.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "리뷰 작성 응답 DTO")
public class ReviewCreateResponseDto {
    @Schema(description = "생성된 리뷰 ID")
    private Long reviewId;
}