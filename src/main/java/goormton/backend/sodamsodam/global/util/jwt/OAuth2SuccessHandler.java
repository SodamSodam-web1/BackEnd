package goormton.backend.sodamsodam.global.util.jwt;

import goormton.backend.sodamsodam.domain.token.domain.RefreshToken;
import goormton.backend.sodamsodam.domain.token.domain.repository.RefreshTokenRepository;
import goormton.backend.sodamsodam.domain.user.domain.UserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtUtil.generateToken(
                userPrincipal.getUser().getId(),
                userPrincipal.getUsername(),
                userPrincipal.getUser().getEmail(),
                userPrincipal.getUser().getRole().getValue()
        );

        String refreshToken = jwtUtil.generateRefreshToken(userPrincipal.getUser().getId());

        // DB에 Refresh Token 저장
        RefreshToken token = RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userPrincipal.getUser().getId())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        refreshTokenRepository.save(token);

        // 프론트엔드에 전달
        response.sendRedirect("http://localhost:3000/oauth2/redirect?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
    }
}
