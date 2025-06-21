package goormton.backend.sodamsodam.domain.review.dto;

import goormton.backend.sodamsodam.domain.review.enums.ReviewTag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "리뷰 작성 요청 DTO")
public class ReviewCreateRequestDto {
    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    @Schema(description = "리뷰 본문 내용", example = "너무 맛있어요.")
    private String content;

    @Schema(enumAsRef = true)
    private ReviewTag tag1;

    @Schema(enumAsRef = true)
    private ReviewTag tag2;

    @Schema(enumAsRef = true)
    private ReviewTag tag3;

    @Schema(description = "이미지 정보 목록 (최대 3개)")
    @Size(max = 3, message = "이미지는 최대 3개까지만 등록할 수 있습니다.")
    private List<ImageInfoDto> images;
}
