package goormton.backend.sodamsodam.domain.review.dto;

import goormton.backend.sodamsodam.domain.review.enums.ReviewTag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "리뷰 수정 요청 DTO")
public class ReviewUpdateRequestDto {
    @Schema(description = "리뷰 본문 내용", example = "맛있어요.")
    private String content;

    @Schema(description = "리뷰 태그 목록 (최대 3개)")
    @Size(max = 3, message = "태그는 최대 3개까지만 등록할 수 있습니다.")
    private List<ReviewTag> tags;

    @Schema(description = "이미지 URL 목록 (최대 3개)")
    @Size(max = 3, message = "이미지는 최대 3개까지만 등록할 수 있습니다.")
    private List<String> imageUrls;
}
