package goormton.backend.sodamsodam.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "jwt 토큰이 반환되는 responseDTO입니다.")
public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {
}
