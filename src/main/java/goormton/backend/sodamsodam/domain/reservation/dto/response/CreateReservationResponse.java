package goormton.backend.sodamsodam.domain.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationResponse(
    Long reservationId,
    LocalDate reservationDate,
    LocalTime reservationTime
) {
}
