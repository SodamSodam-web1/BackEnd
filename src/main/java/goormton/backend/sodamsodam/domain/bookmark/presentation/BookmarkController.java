package goormton.backend.sodamsodam.domain.bookmark.presentation;

import goormton.backend.sodamsodam.domain.bookmark.application.BookmarkService;
import goormton.backend.sodamsodam.domain.bookmark.domain.Bookmark;
import goormton.backend.sodamsodam.domain.bookmark.dto.request.BookmarkedPlaceRequest;
import goormton.backend.sodamsodam.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 북마크 정보 관련 API", description = "로그인한 유저의 북마크를 crud하는 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(
            summary = "북마크 추가",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "로그인된 사용자가 특정 장소를 북마크로 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/places")
    public ResponseCustom<?> addBookmark(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("Authorization") String bearerToken,
            @RequestBody BookmarkedPlaceRequest req
            ) {
        Bookmark bookmark = bookmarkService.addBookmark(bearerToken, req);
        return ResponseCustom.CREATED(bookmark);
    }

    @Operation(summary = "북마크 삭제", description = "로그인된 사용자가 특정 장소의 북마크를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/{bookmarkId}")
    public ResponseCustom<?> removeBookmark(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long bookmarkId
    ) {
        bookmarkService.removeBookmark(bearerToken, bookmarkId);
        return ResponseCustom.OK();
    }
}
