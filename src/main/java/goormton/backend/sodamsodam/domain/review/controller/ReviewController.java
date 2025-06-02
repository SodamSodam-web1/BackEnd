package goormton.backend.sodamsodam.domain.review.controller;

import goormton.backend.sodamsodam.domain.review.dto.*;
import goormton.backend.sodamsodam.domain.review.service.ReviewService;
import goormton.backend.sodamsodam.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Tag(name = "리뷰", description = "리뷰 관련 API")

public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성",description = "특정 장소에 대한 리뷰를 작성합니다.")
    @PostMapping("/places/{placeId}/reviews")
    public ResponseCustom<ReviewCreateResponseDto> createReview(
            //@AuthenticationPrincipal(expression = "userId") Long userId, TODO: JWT 인증 연동 후 해제
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable String placeId,
            @Valid @RequestBody ReviewCreateRequestDto requestDto
    ) {
        ReviewCreateResponseDto responseDto = reviewService.createReview(userId, placeId, requestDto);
        return ResponseCustom.CREATED(responseDto);
    }

    @Operation(summary = "장소 리뷰 목록 조회",description = "특정 장소에 달린 모든 리뷰를 최신순으로 가져옵니다. ")
    @GetMapping("/places/{placeId}/reviews")
    public ResponseCustom<PlaceReviewListResponseDto> getPlaceReviews(
            @PathVariable String placeId,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PlaceReviewListResponseDto responseDto = reviewService.getPlaceReviews(placeId, pageable);
        return ResponseCustom.OK(responseDto);
    }

    @Operation(summary = "리뷰 수정",description = "본인이 작성한 리뷰의 내용을 수정합니다.")
    @PatchMapping("reviews/{reviewId}")
    public ResponseCustom<Void> updateReview(
            //@AuthenticationPrincipal(expression = "userId") Long userId, TODO: JWT 인증 연동 후 해제
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequestDto requestDto
            ){
        reviewService.updateReview(userId, reviewId, requestDto);
        return ResponseCustom.OK();
    }

    @Operation(summary = "리뷰 삭제",description = "본인이 작성한 리뷰를 삭제합니다.")
    @DeleteMapping("reviews/{reviewId}")
    public ResponseCustom<Void> deleteReview(
            //@AuthenticationPrincipal(expression = "userId") Long userId, TODO: JWT 인증 연동 후 해제
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long reviewId
    ){
        reviewService.deleteReview(userId, reviewId);
        return ResponseCustom.OK();
    }
}
