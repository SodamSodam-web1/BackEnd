package goormton.backend.sodamsodam.domain.review.service;

import goormton.backend.sodamsodam.domain.review.dto.PlaceReviewListResponseDto;
import goormton.backend.sodamsodam.domain.review.dto.ReviewCreateRequestDto;
import goormton.backend.sodamsodam.domain.review.dto.ReviewCreateResponseDto;
import goormton.backend.sodamsodam.domain.review.dto.ReviewResponseDto;
import goormton.backend.sodamsodam.domain.review.entity.Image;
import goormton.backend.sodamsodam.domain.review.entity.Review;
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
}
