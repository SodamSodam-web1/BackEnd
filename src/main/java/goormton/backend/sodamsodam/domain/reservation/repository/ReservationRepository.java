package goormton.backend.sodamsodam.domain.reservation.repository;

import goormton.backend.sodamsodam.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByPlaceIdAndReservationDateAndReservationTime(String placeId, LocalDate reservationDate, LocalTime reservationTime);
}
