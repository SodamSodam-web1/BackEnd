package goormton.backend.sodamsodam.domain.bookmark.presentation;

import goormton.backend.sodamsodam.domain.bookmark.application.BookmarkService;
import goormton.backend.sodamsodam.domain.bookmark.domain.Bookmark;
import goormton.backend.sodamsodam.domain.user.dto.response.JwtResponse;
import goormton.backend.sodamsodam.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmakrController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 추가", description = "로그인된 사용자가 특정 장소를 북마크로 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/places/{placeId}")
    public ResponseCustom<?> addBookmark(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("Authorization") String token,
            @PathVariable String placeId
    ) {
        Bookmark bookmark = bookmarkService.addBookmark(token, placeId);
        return ResponseCustom.CREATED(bookmark);
    }

    @Operation(summary = "북마크 삭제", description = "로그인된 사용자가 특정 장소의 북마크를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/{bookmarkId}")
    public ResponseCustom<?> removeBookmark(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("Authorization") String token,
            @PathVariable Long bookmarkId
    ) {
        bookmarkService.removeBookmark(token, bookmarkId);
        return ResponseCustom.OK();
    }
}
