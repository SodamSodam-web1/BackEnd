package goormton.backend.sodamsodam.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "이미지 정보 DTO")
public class ImageInfoDto {
    @Schema(description = "원본 파일명")
    private String originalFileName;

    @Schema(description = "저장 파일명(UUID)")
    private String storedFileName;

    @Schema(description = "이미지 URL")
    private String fileUrl;
} 