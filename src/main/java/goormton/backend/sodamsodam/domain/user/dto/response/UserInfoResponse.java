package goormton.backend.sodamsodam.domain.user.dto.response;

import goormton.backend.sodamsodam.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 정보 조회용 response DTO입니다.")
public record UserInfoResponse(
        String email
) {
    public static UserInfoResponse fromEntity(User user) {
        return new UserInfoResponse(user.getEmail());
    }
}
