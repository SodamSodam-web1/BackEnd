package goormton.backend.sodamsodam.domain.reservation.domain;

import goormton.backend.sodamsodam.domain.common.BaseEntity;
import goormton.backend.sodamsodam.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
public class Reservation extends BaseEntity {

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "place_id")
    private String placeId;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "reservation_time")
    private LocalTime reservationTime;

    protected Reservation() {
    }

    @Builder
    public Reservation(User user, String placeId, LocalDate reservationDate, LocalTime reservationTime) {
        this.user = user;
        this.placeId = placeId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }
}
