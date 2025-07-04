package goormton.backend.sodamsodam.domain.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationListResponse(
        Long reservationId,
        String placeName,
        String addressName,
        String image,
        LocalDate reservationDate,
        LocalTime reservationTime
) {
}
