package goormton.backend.sodamsodam.domain.review.service;

import goormton.backend.sodamsodam.domain.review.dto.*;
import goormton.backend.sodamsodam.domain.review.entity.Image;
import goormton.backend.sodamsodam.domain.review.entity.Review;
import goormton.backend.sodamsodam.domain.review.enums.ReviewTag;
import goormton.backend.sodamsodam.domain.review.repository.ImageRepository;
import goormton.backend.sodamsodam.domain.review.repository.ReviewRepository;
import goormton.backend.sodamsodam.domain.user.entity.User;
import goormton.backend.sodamsodam.domain.user.repository.UserRepository;
import goormton.backend.sodamsodam.global.error.DefaultExeption;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    final private ReviewRepository reviewRepository;
    final private UserRepository userRepository;
    final private ImageRepository imageRepository;

    public ReviewCreateResponseDto createReview(Long userId, String placeId, ReviewCreateRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DefaultExeption(ErrorCode.USER_NOT_FOUND_ERROR));

        Review review = Review.builder()
                .user(user)
                .placeId(placeId)
                .content(dto.getContent())
                .tag1(dto.getTag1())
                .tag2(dto.getTag2())
                .tag3(dto.getTag3())
                .build();

        reviewRepository.save(review);

        List<Image> images= dto.getImageUrls().stream()
                        .map(url->Image.builder()
                                .url(url)
                                .review(review)
                                .build()).toList();

        imageRepository.saveAll(images);

        return new ReviewCreateResponseDto(review.getId());
    }

    @Transactional(readOnly = true)
    public PlaceReviewListResponseDto getPlaceReviews(String placeId, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllByPlaceIdWithUser(placeId, pageable);

        List<ReviewResponseDto> reviewDtos = reviewPage.map(review -> {
            String username = review.getUser().getName();
            List<String> imageUrls=imageRepository.findUrlsByReviewId(review.getId());
            return ReviewResponseDto.from(review, username, imageUrls);
        }).getContent();

        long totalCount = reviewPage.getTotalElements();

        return PlaceReviewListResponseDto.builder()
                .placeId(placeId)
                .reviews(reviewDtos)
                .hasNext(reviewPage.hasNext())
                .totalCount(totalCount)
                .build();
    }

    @Transactional
    public ReviewUpdateResponseDto updateReview(Long userId, Long reviewId, ReviewUpdateRequestDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new DefaultExeption(ErrorCode.REVIEW_NOT_FOUND_ERROR));

        if (!review.getUser().getId().equals(userId)) {
            throw new DefaultExeption(ErrorCode.FORBIDDEN_REVIEW_UPDATE);
        }

        Review.ReviewBuilder builder = review.toBuilder();

        if (dto.getContent() != null) {
            builder.content(dto.getContent());
        }

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            List<ReviewTag> tags = dto.getTags();
            builder
                    .tag1(tags.get(0))
                    .tag2(tags.size() > 1 ? tags.get(1) : null)
                    .tag3(tags.size() > 2 ? tags.get(2) : null);
        }

        Review updatedReview = builder.build();

        review.update(
                updatedReview.getContent(),
                updatedReview.getTag1(),
                updatedReview.getTag2(),
                updatedReview.getTag3()
        );

        if (dto.getImageUrls() != null) {
            imageRepository.deleteAllByReview(review);

            List<Image> newImages = dto.getImageUrls().stream()
                    .map(url -> Image.builder()
                            .url(url)
                            .review(review)
                            .build())
                    .toList();

            imageRepository.saveAll(newImages);
        }

        return new ReviewUpdateResponseDto(review.getId());
    }
}
