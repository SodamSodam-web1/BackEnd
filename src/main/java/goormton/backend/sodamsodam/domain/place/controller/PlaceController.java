package goormton.backend.sodamsodam.domain.place.controller;

import goormton.backend.sodamsodam.domain.place.dto.PlaceResponseDto;
import goormton.backend.sodamsodam.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/keyword")
    public ResponseEntity<List<PlaceResponseDto>> searchPlaces(
            @RequestParam String query,
            @RequestParam(required = false) String x,
            @RequestParam(required = false) String y,
            @RequestParam(required = false) Integer radius) {

        List<PlaceResponseDto> places = placeService.searchPlaces(query, x, y, radius);
        return ResponseEntity.ok(places);
    }

    @GetMapping("/category")
    public ResponseEntity<List<PlaceResponseDto>> searchByCategory(
            @RequestParam String category_group_code,
            @RequestParam(required = false) String x,
            @RequestParam(required = false) String y,
            @RequestParam(required = false) Integer radius) {

        List<PlaceResponseDto> places = placeService.searchByCategory(category_group_code, x, y, radius);
        return ResponseEntity.ok(places);
    }
}
