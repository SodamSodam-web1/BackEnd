package goormton.backend.sodamsodam.domain.reservation.repository;

import goormton.backend.sodamsodam.domain.reservation.domain.Reservation;
import goormton.backend.sodamsodam.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Long user(User user);
}
