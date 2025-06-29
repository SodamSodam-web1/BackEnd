package goormton.backend.sodamsodam.domain.review.controller;

import goormton.backend.sodamsodam.domain.review.dto.*;
import goormton.backend.sodamsodam.domain.review.service.ReviewService;
import goormton.backend.sodamsodam.global.payload.ResponseCustom;
import goormton.backend.sodamsodam.global.service.S3Service;
import goormton.backend.sodamsodam.global.util.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "리뷰", description = "리뷰 관련 API")

public class ReviewController {
    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;
    private final S3Service s3Service;

    @Operation(summary = "리뷰 이미지 업로드", description = "리뷰에 첨부할 이미지를 업로드합니다.")
    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseCustom<List<S3Service.S3UploadResult>> uploadReviewImage(
            @Parameter(description = "AccessToken을 입력해주세요", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "이미지 파일", required = true)
            @RequestParam("file") List<MultipartFile> files
    ) {
        List<S3Service.S3UploadResult> results = files.stream()
                .map(s3Service::uploadFile)
                .toList();
        return ResponseCustom.OK(results);
    }

    @Operation(summary = "리뷰 작성", description = "로그인된 사용자가 특정 장소에 리뷰를 작성합니다.")
    @PostMapping("/places/{placeId}/reviews")
    public ResponseCustom<ReviewCreateResponseDto> createReview(
            @Parameter(description = "AccessToken을 입력해주세요", required = true)
            @RequestHeader("Authorization") String token,
            @PathVariable String placeId,
            @Valid @RequestBody ReviewCreateRequestDto requestDto
    ) {
        ReviewCreateResponseDto responseDto = reviewService.createReview(token, placeId, requestDto);
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
    @PatchMapping("/reviews/{reviewId}")
    public ResponseCustom<Void> updateReview(
            @Parameter(description = "AccessToken을 입력해주세요", required = true)
            @RequestHeader("Authorization") String token,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequestDto requestDto
    ){
        reviewService.updateReview(token, reviewId, requestDto);
        return ResponseCustom.OK();
    }

    @Operation(summary = "리뷰 삭제",description = "본인이 작성한 리뷰를 삭제합니다.")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseCustom<Void> deleteReview(
            @Parameter(description = "AccessToken을 입력해주세요", required = true)
            @RequestHeader("Authorization") String token,
            @PathVariable Long reviewId
    ){
        reviewService.deleteReview(token, reviewId);
        return ResponseCustom.OK();
    }

    @Operation(summary = "본인 리뷰 단건 조회", description = "리뷰 수정 전, 본인이 작성한 리뷰의 내용을 불러옵니다.")
    @GetMapping("/reviews/{reviewId}")
    public ResponseCustom<ReviewEditResponseDto> getReviewForEdit(
            @Parameter(description = "AccessToken을 입력해주세요", required = true)
            @RequestHeader("Authorization") String token,
            @PathVariable Long reviewId
    ) {
        ReviewEditResponseDto responseDto = reviewService.getReviewForEdit(token, reviewId);
        return ResponseCustom.OK(responseDto);
    }

    @Operation(summary = "내 리뷰 목록 조회",description = "로그인한 사용자가 작성한 리뷰 목록을 최신순으로 조회합니다. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 리뷰 목록 조회 성공"),
    })
    @GetMapping("/my/reviews")
    public ResponseCustom<MyReviewListResponseDto> getMyReviews(
            @Parameter(description = "AccessToken을 입력해주세요", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        MyReviewListResponseDto responseDto = reviewService.getMyReviews(token, pageable);
        return ResponseCustom.OK(responseDto);
    }
}
