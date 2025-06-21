package goormton.backend.sodamsodam.domain.bookmark.application;

import goormton.backend.sodamsodam.domain.bookmark.domain.Bookmark;
import goormton.backend.sodamsodam.domain.bookmark.domain.repository.BookmarkRepository;
import goormton.backend.sodamsodam.domain.bookmark.dto.request.BookmarkedPlaceRequest;
import goormton.backend.sodamsodam.domain.bookmark.dto.response.BookmarkedPlaceResponse;
import goormton.backend.sodamsodam.domain.review.repository.ImageRepository;
import goormton.backend.sodamsodam.domain.user.domain.User;
import goormton.backend.sodamsodam.domain.user.domain.repository.UserRepository;
import goormton.backend.sodamsodam.global.error.DefaultException;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import goormton.backend.sodamsodam.global.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public Bookmark addBookmark(String bearerToken, BookmarkedPlaceRequest req) {
        String token = jwtUtil.getJwt(bearerToken);
        Long userId = jwtUtil.getIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));
        bookmarkRepository.findByUserAndPlaceId(user, req.placeId())
                .ifPresent(b -> {
                    throw new DefaultException(ErrorCode.BOOKMARK_ALREADY_EXISTS_ERROR);
                });

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .placeId(req.placeId())
                .placeName(req.placeName())
                .addressName(req.addressName())
                .phone(req.phone())
                .build();

        return bookmarkRepository.save(bookmark);
    }

    @Transactional
    @Override
    public void removeBookmark(String bearerToken, Long bookmarkId) {
        String token = jwtUtil.getJwt(bearerToken);
        Long userId = jwtUtil.getIdFromToken(token);
        userRepository.findById(userId).orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));
        Bookmark existing = bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new DefaultException(ErrorCode.BOOKMARK_NOT_FOUND_ERROR));
        bookmarkRepository.delete(existing);
    }

    @Transactional
    @Override
    public List<BookmarkedPlaceResponse> getAllBookmarkedPlaces(String bearerToken) {
        String token = jwtUtil.getJwt(bearerToken);
        Long userId = jwtUtil.getIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

        // DB에서 사용자가 가진 북마크 엔티티들 조회
        List<BookmarkedPlaceResponse> bookmarks = bookmarkRepository.findAllByUser(user).stream()
                .map(b -> {
                    // placeId 별 review의 최근 사진 3개 조회
                    List<String> urls = imageRepository.findTop3UrlsByPlaceId(b.getPlaceId());

                    // dto return
                    return BookmarkedPlaceResponse.builder()
                            .placeId(b.getPlaceId())
                            .placeName(b.getPlaceName())
                            .addressName(b.getAddressName())
                            .phone(b.getPhone())
                            .imageUrls(urls)
                            .bookmarkedAt(b.getCreatedAt())
                            .build();
                })
                .toList();
        if (bookmarks.isEmpty()) {
            return Collections.emptyList();
        }

        return bookmarks;
    }
}
