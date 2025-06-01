package goormton.backend.sodamsodam.domain.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchHistoryDto {
    private String searchContent;
    private LocalDateTime createdAt;
} 