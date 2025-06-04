package goormton.backend.sodamsodam.domain.bookmark.domain.repository;

import goormton.backend.sodamsodam.domain.bookmark.domain.Bookmark;
import goormton.backend.sodamsodam.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // 특정 사용자가 특정 place_id를 북마크했는지 확인
    Optional<Bookmark> findByUserAndPlaceId(User user, String placeId);

    // 특정 사용자가 저장한 모든 Bookmark 목록
    List<Bookmark> findAllByUser(User user);
}
