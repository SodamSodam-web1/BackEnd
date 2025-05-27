package goormton.backend.sodamsodam.domain.review.controller;

import goormton.backend.sodamsodam.domain.review.dto.ReviewCreateRequestDto;
import goormton.backend.sodamsodam.domain.review.dto.ReviewCreateResponseDto;
import goormton.backend.sodamsodam.domain.review.service.ReviewService;
import goormton.backend.sodamsodam.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
