package goormton.backend.sodamsodam.domain.reservation.service;

import goormton.backend.sodamsodam.domain.reservation.domain.Reservation;
import goormton.backend.sodamsodam.domain.reservation.dto.request.CreateReservationRequest;
import goormton.backend.sodamsodam.domain.reservation.dto.response.CreateReservationResponse;
import goormton.backend.sodamsodam.domain.reservation.repository.ReservationRepository;
import goormton.backend.sodamsodam.domain.user.domain.User;
import goormton.backend.sodamsodam.domain.user.domain.repository.UserRepository;
import goormton.backend.sodamsodam.global.error.DefaultAuthenticationException;
import goormton.backend.sodamsodam.global.error.DefaultException;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import goormton.backend.sodamsodam.global.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * 예약 생성 메서드
     * @param request
     * @param createReservationRequest
     * @return 생성된 예약 정보 (reservationId, reservationDate, reservationTime)
     */
    public CreateReservationResponse createReservation(HttpServletRequest request, CreateReservationRequest createReservationRequest) {
        String token = jwtUtil.getJwt(request);

        // Todo JWT 관련 로직 분리 필요
        if (!jwtUtil.validateToken(token)) {
            throw new DefaultAuthenticationException(ErrorCode.JWT_EXPIRED_ERROR);
        }

        Long userId = jwtUtil.getIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

        Reservation reservation = Reservation.builder()
                .user(user)
                .placeId(createReservationRequest.placeId())
                .reservationDate(createReservationRequest.reservationDate())
                .reservationTime(createReservationRequest.reservationTime())
                .build();

        reservationRepository.save(reservation);

        return new CreateReservationResponse(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getReservationTime()
        );
    }

    /**
     * 예약 취소 메서드
     * 흐름: 헤더에서 토큰 추출 → 토큰 검증 → 삭제 요청 들어온 예약 존재 여부 검증 → 삭제 요청한 사용자와 예약한 사용자 일치 여부 검증
     * @param request
     * @param reservationId
     */
    @Transactional
    public void deleteReservation(HttpServletRequest request, Long reservationId) {
        String token = jwtUtil.getJwt(request);

        // Todo JWT 관련 로직 분리 필요
        if (!jwtUtil.validateToken(token)) {
            throw new DefaultAuthenticationException(ErrorCode.JWT_EXPIRED_ERROR);
        }

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new DefaultException(ErrorCode.RESERVATION_NOT_FOUND));

        Long userId = jwtUtil.getIdFromToken(token);
        if (!reservation.getUser().getId().equals(userId)) {
            throw new DefaultException(ErrorCode.FORBIDDEN_RESERVATION_DELETE);
        }

        reservationRepository.deleteById(reservationId);
    }
}
