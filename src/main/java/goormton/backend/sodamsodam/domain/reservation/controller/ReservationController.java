package goormton.backend.sodamsodam.domain.reservation.controller;

import goormton.backend.sodamsodam.domain.reservation.dto.request.CreateReservationRequest;
import goormton.backend.sodamsodam.domain.reservation.dto.response.CreateReservationResponse;
import goormton.backend.sodamsodam.domain.reservation.service.ReservationService;
import goormton.backend.sodamsodam.global.payload.ResponseCustom;
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
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CreateReservationRequest createReservationRequest
    ) {
        CreateReservationResponse createReservationResponse = reservationService.createReservation(authHeader, createReservationRequest);
        return ResponseCustom.CREATED(createReservationResponse);
    }
}
