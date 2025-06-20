package goormton.backend.sodamsodam.domain.reservation.controller;

import goormton.backend.sodamsodam.domain.reservation.dto.request.CreateReservationRequest;
import goormton.backend.sodamsodam.domain.reservation.dto.response.CreateReservationResponse;
import goormton.backend.sodamsodam.domain.reservation.service.ReservationService;
import goormton.backend.sodamsodam.global.payload.ResponseCustom;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseCustom<CreateReservationResponse> createReservation(
            HttpServletRequest request,
            @RequestBody @Valid CreateReservationRequest createReservationRequest
    ) {
        CreateReservationResponse createReservationResponse = reservationService.createReservation(request, createReservationRequest);
        return ResponseCustom.CREATED(createReservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseCustom<?> deleteReservation(
            HttpServletRequest request,
            @PathVariable("id") Long reservationId
    ) {
        reservationService.deleteReservation(request, reservationId);
        return ResponseCustom.OK();
    }
}
