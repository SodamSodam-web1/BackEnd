package goormton.backend.sodamsodam.domain.bookmark.application;

import goormton.backend.sodamsodam.domain.bookmark.domain.Bookmark;

public interface BookmarkService {

    // 유저로부터 요청 받아 북마크를 생성
    public Bookmark addBookmark(String token, String placeId);

    public void removeBookmark(String token, Long bookmarkId);
}
