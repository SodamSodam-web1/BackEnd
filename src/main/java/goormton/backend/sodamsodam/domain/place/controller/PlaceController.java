package goormton.backend.sodamsodam.domain.place.controller;

import goormton.backend.sodamsodam.domain.place.dto.PlaceResponseDto;
import goormton.backend.sodamsodam.domain.place.dto.SearchHistoryDto;
import goormton.backend.sodamsodam.domain.place.service.PlaceService;
import goormton.backend.sodamsodam.domain.user.domain.UserPrincipal;
import goormton.backend.sodamsodam.global.error.DefaultException;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
@Tag(name = "Place", description = "장소 검색 API")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "키워드로 장소 검색", description = "키워드를 기반으로 장소를 검색합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공",
            content = @Content(schema = @Schema(implementation = PlaceResponseDto.class))),
    })
    @GetMapping("/keyword")
    public ResponseEntity<List<PlaceResponseDto>> searchByKeyword(
            @Parameter(description = "검색 키워드", required = true) @RequestParam String query,
            @Parameter(description = "중심 좌표의 X 혹은 경도(longitude) 값\n" +
                    "특정 지역을 중심으로 검색할 경우 radius와 함께 사용 가능)") @RequestParam(required = false) String x,
            @Parameter(description = "중심 좌표의 Y 혹은 위도(latitude) 값\n" +
                    "특정 지역을 중심으로 검색할 경우 radius와 함께 사용 가능") @RequestParam(required = false) String y,
            @Parameter(description = "중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 중심좌표로 쓰일 x,y와 함께 사용\n" +
                    "(단위: 미터(m), 최소: 0, 최대: 20000)") @RequestParam(required = false) Integer radius) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PlaceResponseDto> places = placeService.searchByKeyword(query, x, y, radius, userPrincipal.getUser());
        return ResponseEntity.ok(places);
    }

    @Operation(summary = "카테고리로 장소 검색", description = "카테고리 코드를 기반으로 장소를 검색합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공",
            content = @Content(schema = @Schema(implementation = PlaceResponseDto.class))),
    })
    @GetMapping("/category")
    public ResponseEntity<List<PlaceResponseDto>> searchByCategory(
            @Parameter(description = "카테고리 그룹 코드", required = true) @RequestParam String category_group_code,
            @Parameter(description = "중심 좌표의 X값 혹은 longitude\n" +
                    "특정 지역을 중심으로 검색하려고 할 경우 radius와 함께 사용 가능.") @RequestParam(required = false) String x,
            @Parameter(description = "중심 좌표의 Y값 혹은 latitude\n" +
                    "특정 지역을 중심으로 검색하려고 할 경우 radius와 함께 사용 가능.") @RequestParam(required = false) String y,
            @Parameter(description = "중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 중심좌표로 쓰일 x,y와 함께 사용\n" +
                    "(단위: 미터(m), 최소: 0, 최대: 20000)") @RequestParam(required = false) Integer radius) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PlaceResponseDto> places = placeService.searchByCategory(category_group_code, x, y, radius, userPrincipal.getUser());
        return ResponseEntity.ok(places);
    }

    @Operation(summary = "검색 기록 조회", description = "저장된 검색 기록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 기록 조회 성공",
            content = @Content(schema = @Schema(implementation = SearchHistoryDto.class)))
    })
    @GetMapping("/search-histories")
    public ResponseEntity<List<SearchHistoryDto>> getSearchHistories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getPrincipal() instanceof String) {
            throw new DefaultException(ErrorCode.INVALID_AUTHENTICATION);
        }
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(placeService.getSearchHistories(userPrincipal.getUser()));
    }
}
