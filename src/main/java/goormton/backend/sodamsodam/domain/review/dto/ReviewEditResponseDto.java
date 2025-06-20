package goormton.backend.sodamsodam.domain.review.dto;

import goormton.backend.sodamsodam.domain.review.enums.ReviewTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "리뷰 수정 폼 조회 응답 DTO")
public class ReviewEditResponseDto {

    @Schema(description = "리뷰 본문 내용", example = "맛있어요.")
    private String content;

    @Schema(description = "리뷰 태그 목록 (최대 3개)")
    private List<ReviewTag> tags;

    @Schema(description = "이미지 정보 목록 (최대 3개)")
    private List<ImageInfoDto> images;
}