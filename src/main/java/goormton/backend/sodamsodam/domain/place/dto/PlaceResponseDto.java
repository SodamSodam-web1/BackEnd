package goormton.backend.sodamsodam.domain.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDto {

    @Schema(description = "카카오 장소 ID", example = "26338954")
    private String id;

    @Schema(description = "장소명 또는 업체명", example = "카카오프렌즈 롯데월드몰 잠실점")
    private String placeName;

    @Schema(description = "전화번호", example = "02-3213-4514")
    private String phone;

    @Schema(description = "전체 지번 주소", example = "서울 송파구 신천동 29")
    private String addressName;

    @Schema(description = "장소 리뷰 이미지 URL 목록 (해당 장소의 모든 리뷰 이미지)")
    private List<String> imageUrls;

    public static PlaceResponseDto from(KakaoPlaceDto.Document document, List<String> imageUrls) {
        return new PlaceResponseDto(
                document.getId(),
                document.getPlace_name(),
                document.getPhone(),
                document.getAddress_name(),
                imageUrls
        );
    }
}
