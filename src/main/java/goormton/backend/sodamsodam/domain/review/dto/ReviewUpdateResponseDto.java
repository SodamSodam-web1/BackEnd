package goormton.backend.sodamsodam.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "리뷰 수정 응답 DTO")
public class ReviewUpdateResponseDto {
    @Schema(description = "수정된 리뷰 ID")
    private Long reviewId;
}
