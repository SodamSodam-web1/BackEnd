package goormton.backend.sodamsodam.domain.review.repository;

import goormton.backend.sodamsodam.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.placeId = :placeId")
    Page<Review> findAllByPlaceIdWithUser(@Param("placeId") String placeId, Pageable pageable);
}
