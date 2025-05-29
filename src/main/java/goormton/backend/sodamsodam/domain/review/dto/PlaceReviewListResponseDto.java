package goormton.backend.sodamsodam.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "장소별 리뷰 목록 응답 DTO")
public class PlaceReviewListResponseDto {
    @Schema(description = "장소 ID")
    private String placeId;

    @Schema(description = "리뷰 응답 리스트")
    private List<ReviewResponseDto> reviews;

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    private boolean hasNext;

    @Schema(description = "전체 리뷰 개수")
    private long totalCount;
}
