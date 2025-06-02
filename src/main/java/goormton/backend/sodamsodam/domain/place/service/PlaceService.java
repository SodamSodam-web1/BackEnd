package goormton.backend.sodamsodam.domain.place.service;

import goormton.backend.sodamsodam.domain.place.dto.KakaoPlaceDto;
import goormton.backend.sodamsodam.domain.place.dto.PlaceResponseDto;
import goormton.backend.sodamsodam.domain.place.entity.Search;
import goormton.backend.sodamsodam.domain.place.repository.SearchRepository;
import goormton.backend.sodamsodam.domain.place.dto.SearchHistoryDto;
import goormton.backend.sodamsodam.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {

    @Qualifier("kakaoWebClient")
    private final WebClient webClient;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    private final SearchRepository searchRepository;

    public List<PlaceResponseDto> searchByKeyword(String query, String x, String y, Integer radius) {
        searchRepository.save(new Search(query, null));
        try {
            KakaoPlaceDto response = webClient.get()
                    .uri(uriBuilder -> {
                        URI uri = uriBuilder
                                .path("/v2/local/search/keyword.json")
                                .queryParam("query", query)
                                .queryParam("x", x)
                                .queryParam("y", y)
                                .queryParam("radius", radius != null ? radius : 10000)
                                .build();
                        return uri;
                    })
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .retrieve()
                    .bodyToMono(KakaoPlaceDto.class)
                    .doOnError(error -> log.error("Kakao API Error: {}", error.getMessage()))
                    .block();


            if (response != null && response.getDocuments() != null) {
                return response.getDocuments().stream()
                        .map(PlaceResponseDto::from)
                        .collect(Collectors.toList());
            }

            return List.of();

        } catch (Exception e) {
            log.error("Error occurred while searching places: ", e);
            return List.of();
        }
    }

    public List<PlaceResponseDto> searchByCategory(
            String category_group_code,
            String x,
            String y,
            Integer radius) {
        searchRepository.save(new Search(category_group_code, null));
        try {
            KakaoPlaceDto response = webClient.get()
                    .uri(uriBuilder -> {
                        URI uri = uriBuilder
                                .path("/v2/local/search/category.json")
                                .queryParam("category_group_code", category_group_code)
                                .queryParam("x", x)
                                .queryParam("y", y)
                                .queryParam("radius", radius != null ? radius : 10000)
                                .build();
                        log.info("Request URI: {}", uri);
                        return uri;
                    })
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .retrieve()
                    .bodyToMono(KakaoPlaceDto.class)
                    .doOnError(error -> log.error("Kakao API Error: {}", error.getMessage()))
                    .block();


            if (response != null && response.getDocuments() != null) {
                return response.getDocuments().stream()
                        .map(PlaceResponseDto::from)
                        .collect(Collectors.toList());
            }

            return List.of();

        } catch (Exception e) {
            log.error("Error occurred while searching by category: ", e);
            return List.of();
        }
    }

    public List<SearchHistoryDto> getSearchHistories(User user) {
        return searchRepository.findAllByUser(user).stream()
                .map(s -> new SearchHistoryDto(s.getSearchContent(), s.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
