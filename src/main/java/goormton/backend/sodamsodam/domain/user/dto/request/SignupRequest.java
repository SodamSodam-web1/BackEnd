package goormton.backend.sodamsodam.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "유저 회원가입용 requestDTO입니다.")
public record SignupRequest(
        @NotBlank(message = "아이디는 필수입니다.")
        @Size(min = 3, max = 10, message = "아이디는 3 ~ 13글자여야 합니다.")
        String username,

        @Email
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 15, message = "비밀번호는 8 ~ 15글자여야 합니다.")
        String password
) {
}
