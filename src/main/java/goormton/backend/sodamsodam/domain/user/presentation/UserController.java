package goormton.backend.sodamsodam.domain.user.presentation;

import goormton.backend.sodamsodam.domain.bookmark.application.BookmarkService;
import goormton.backend.sodamsodam.domain.bookmark.dto.response.BookmarkedPlaceResponse;
import goormton.backend.sodamsodam.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "유저 정보 관련 API", description = "유저의 개인정보, 서비스 관련 정보를 확인하는 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/my")
@RestController
public class UserController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "유저의 북마크 리스트 조회", description = "로그인된 사용자가 체크한 특정 장소의 북마크 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/bookmarks")
    public ResponseCustom<?> getMyBookmarks(
            HttpServletRequest request
    ) {
        List<BookmarkedPlaceResponse> resp = bookmarkService.getAllBookmarkedPlaces(request);
        return ResponseCustom.OK(resp);
    }
}
