package goormton.backend.sodamsodam.domain.place.dto;

import lombok.Data;
import java.util.List;

@Data
public class KakaoPlaceDto {

    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {
        private int total_count;      // 검색된 문서 수
        private int pageable_count;   // total_count 중 노출 가능 문서 수
        private boolean is_end;       // 현재 페이지가 마지막 페이지인지 여부
    }

    @Data
    public static class Document {
        private String id;
        private String place_name;         // 장소명
        private String category_name;      // 카테고리 이름
        private String category_group_code; // 카테고리 그룹 코드 (ex. FD6, CE7 등)
        private String category_group_name; // 카테고리 그룹명
        private String phone;              // 전화번호
        private String address_name;       // 지번 주소
        private String road_address_name;  // 도로명 주소
        private String place_url;          // 장소 상세 페이지 URL
        private String distance;           // 중심 좌표까지의 거리 (category 검색 시에만 제공)
        private String x;                  // 경도
        private String y;                  // 위도
    }
}