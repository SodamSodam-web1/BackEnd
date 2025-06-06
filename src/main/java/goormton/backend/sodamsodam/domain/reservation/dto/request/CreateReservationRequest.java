package goormton.backend.sodamsodam.domain.reservation.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequest(
        String placeId,
        LocalDate reservationDate,
        LocalTime reservationTime
) {
}
