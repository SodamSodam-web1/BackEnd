package goormton.backend.sodamsodam.domain.bookmark.application;

import goormton.backend.sodamsodam.domain.bookmark.domain.Bookmark;
import goormton.backend.sodamsodam.domain.bookmark.dto.request.BookmarkedPlaceRequest;
import goormton.backend.sodamsodam.domain.bookmark.dto.response.BookmarkedPlaceResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BookmarkService {

    // 유저로부터 요청 받아 북마크를 생성
    Bookmark addBookmark(String bearerToken, BookmarkedPlaceRequest req);

    // 유저로부터 delete 요청을 받아 북마크를 삭제
    void removeBookmark(String bearerToken, Long bookmarkId);

    // 유저 인증 후 유저의 북마크 리스트 조회
    List<BookmarkedPlaceResponse> getAllBookmarkedPlaces(String bearerToken);
}
