package goormton.backend.sodamsodam.domain.review.repository;

import goormton.backend.sodamsodam.domain.review.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i.url FROM Image i WHERE i.review.id = :id")
    List<String> findUrlsByReviewId(@Param("id") Long reviewId);
}
