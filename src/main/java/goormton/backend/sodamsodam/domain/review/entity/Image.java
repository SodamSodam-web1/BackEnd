package goormton.backend.sodamsodam.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFileName;    // 원본 파일명

    @Column(nullable = false)
    private String storedFileName;      // S3에 저장된 파일명 (UUID 등으로 생성)

    @Column(nullable = false)
    private String fileUrl;             // S3 URL

    @Column(nullable = false)
    private String filePath;            // S3 내부 경로

    @Column(nullable = false)
    private String fileType;            // 파일 타입 (image/jpeg, image/png 등)

    @Column(nullable = false)
    private Long fileSize;              // 파일 크기 (bytes)

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
}
