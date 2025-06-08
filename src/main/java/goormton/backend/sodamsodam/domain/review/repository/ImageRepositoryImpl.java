package goormton.backend.sodamsodam.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import goormton.backend.sodamsodam.domain.review.entity.QImage;
import goormton.backend.sodamsodam.domain.review.entity.QReview;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findTop3UrlsByPlaceId(String placeId) {
        QImage image = QImage.image;
        QReview review = QReview.review;

        return queryFactory
                .select(image.url)
                .from(image)
                .join(image.review, review)
                .where(review.placeId.eq(placeId))
                .orderBy(image.createdAt.desc())
                .limit(3)
                .fetch();
    }
}
