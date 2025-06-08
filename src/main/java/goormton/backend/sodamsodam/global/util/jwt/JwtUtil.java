package goormton.backend.sodamsodam.global.util.jwt;

import goormton.backend.sodamsodam.global.error.DefaultException;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationInMs;
    private final long refreshTokenExpirationInMs;

    public JwtUtil(@Value("${app.auth.token-secret}") String secretKey,
                   @Value("${app.auth.access-token-expiration-msec}") long jwtExpirationInMs,
                   @Value("${app.auth.refresh-token-expiration-msec}") long refreshTokenExpirationInMs) {

        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.refreshTokenExpirationInMs = refreshTokenExpirationInMs;
    }

//    Jwt 토큰 생성
    public String generateToken(Long userId, String username, String email, String role) {

        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationInMs))
                .signWith(secretKey)
                .compact();
    }

    // token 추출
    public String getJwt(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        throw new DefaultException(ErrorCode.INVALID_PARAMETER, "Authorization header is missing");
    }

//    jwt로부터 정보 추출
    public Long getIdFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", Long.class);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRoleFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

//    jwt 유효성 검증
    public Boolean validateToken(String token) {
        isTokenExpired(token);
        return true;
    }

//    jwt 토큰 만료 여부 확인
    public Boolean isTokenExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new DefaultException(ErrorCode.JWT_EXPIRED_ERROR);
        } catch (JwtException e) {
            throw new DefaultException(ErrorCode.INVALID_JWT_ERROR);
        }
    }
}
