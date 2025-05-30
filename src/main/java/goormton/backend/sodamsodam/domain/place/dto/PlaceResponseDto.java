package goormton.backend.sodamsodam.domain.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDto {
    private String placeName;   // 장소명, 업체명
    private String phone;       // 전화 번호
    private String addressName; // 전체 지번 주소

    public static PlaceResponseDto from(KakaoApiResponseDto.Document document) {
        return new PlaceResponseDto(
                document.getPlace_name(),
                document.getPhone(),
                document.getAddress_name()
        );
    }
}
