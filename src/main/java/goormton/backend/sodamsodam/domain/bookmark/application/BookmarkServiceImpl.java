package goormton.backend.sodamsodam.domain.bookmark.application;

import goormton.backend.sodamsodam.domain.bookmark.domain.Bookmark;
import goormton.backend.sodamsodam.domain.bookmark.domain.repository.BookmarkRepository;
import goormton.backend.sodamsodam.domain.user.domain.User;
import goormton.backend.sodamsodam.domain.user.domain.repository.UserRepository;
import goormton.backend.sodamsodam.global.error.DefaultException;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import goormton.backend.sodamsodam.global.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
//    private final WebClient kakaoWebClient;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public Bookmark addBookmark(String token, String placeId) {
        Long userId = jwtUtil.getIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));
        bookmarkRepository.findByUserAndPlaceId(user, placeId)
                .ifPresent(b -> {
                    throw new DefaultException(ErrorCode.BOOKMARK_ALREADY_EXISTS_ERROR);
                });

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .placeId(placeId)
                .build();

        return bookmarkRepository.save(bookmark);
    }

    @Override
    public void removeBookmark(String token, Long bookmarkId) {
        Long userId = jwtUtil.getIdFromToken(token);
        userRepository.findById(userId).orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));
        Bookmark existing = bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new DefaultException(ErrorCode.BOOKMARK_NOT_FOUND_ERROR));
        bookmarkRepository.delete(existing);
    }
}
