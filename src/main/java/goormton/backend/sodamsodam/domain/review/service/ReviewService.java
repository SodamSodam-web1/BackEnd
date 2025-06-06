package goormton.backend.sodamsodam.domain.review.service;

import goormton.backend.sodamsodam.domain.review.dto.*;
import goormton.backend.sodamsodam.domain.review.entity.Image;
import goormton.backend.sodamsodam.domain.review.entity.Review;
import goormton.backend.sodamsodam.domain.review.enums.ReviewTag;
import goormton.backend.sodamsodam.domain.review.repository.ImageRepository;
import goormton.backend.sodamsodam.domain.review.repository.ReviewRepository;
import goormton.backend.sodamsodam.domain.user.domain.User;
import goormton.backend.sodamsodam.domain.user.domain.repository.UserRepository;
import goormton.backend.sodamsodam.global.error.DefaultException;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReviewService {
    final private ReviewRepository reviewRepository;
    final private UserRepository userRepository;
    final private ImageRepository imageRepository;

    public ReviewCreateResponseDto createReview(Long userId, String placeId, ReviewCreateRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

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
            String username = review.getUser().getUsername();
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
    public void updateReview(Long userId, Long reviewId, ReviewUpdateRequestDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new DefaultException(ErrorCode.REVIEW_NOT_FOUND_ERROR));

        if (!review.getUser().getId().equals(userId)) {
            throw new DefaultException(ErrorCode.FORBIDDEN_REVIEW_UPDATE);
        }

        List<ReviewTag> tags = dto.getTags();
        if (tags != null && !tags.isEmpty()) {
            validateDuplicateTags(tags);
        }

        ReviewTag tag1 = tags != null && !tags.isEmpty() ? tags.get(0) : null;
        ReviewTag tag2 = tags != null && tags.size() > 1 ? tags.get(1) : null;
        ReviewTag tag3 = tags != null && tags.size() > 2 ? tags.get(2) : null;

        review.update(dto.getContent(), tag1, tag2, tag3);

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
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new DefaultException(ErrorCode.REVIEW_NOT_FOUND_ERROR));

        if (!review.getUser().getId().equals(userId)) {
            throw new DefaultException(ErrorCode.FORBIDDEN_REVIEW_DELETE);
        }

        imageRepository.deleteAllByReview(review);
        reviewRepository.delete(review);
    }

    private void validateDuplicateTags(List<ReviewTag> tags) {
        if (tags == null) return;

        Set<ReviewTag> tagSet = new HashSet<>(tags);
        if (tagSet.size() != tags.size()) {
            throw new DefaultException(ErrorCode.DUPLICATE_REVIEW_TAGS);
        }
    }

    @Transactional(readOnly = true)
    public ReviewEditResponseDto getReviewForEdit(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new DefaultException(ErrorCode.REVIEW_NOT_FOUND_ERROR));

        if (!review.getUser().getId().equals(userId)) {
            throw new DefaultException(ErrorCode.FORBIDDEN_REVIEW_UPDATE);
        }

        List<String> imageUrls = imageRepository.findUrlsByReviewId(reviewId);

        List<ReviewTag> tags = new ArrayList<>();
        if (review.getTag1() != null) tags.add(review.getTag1());
        if (review.getTag2() != null) tags.add(review.getTag2());
        if (review.getTag3() != null) tags.add(review.getTag3());

        return ReviewEditResponseDto.builder()
                .content(review.getContent())
                .tags(tags)
                .imageUrls(imageUrls)
                .build();
    }
}
