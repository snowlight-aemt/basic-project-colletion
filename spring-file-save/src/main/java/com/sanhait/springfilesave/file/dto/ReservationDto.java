
package com.sanhait.springfilesave.file.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationDto implements Serializable {
    private int seqNo;
    private String roomNo;
    private String reservationStatus;
    private String status;
    private LocalDateTime createdAt;
    private String message = "";

    public ReservationDto(int seqNo, String roomNo, String reservationStatus, String status, LocalDateTime createdAt, String message) {
        this.seqNo = seqNo;
        this.roomNo = roomNo;
        this.reservationStatus = reservationStatus;
        this.status = status;
        this.createdAt = createdAt;
        this.message = message;
    }

    public static ReservationDto dto(Reservation reservation) {
        return new ReservationDto(
                reservation.getSeqNo(),
                reservation.getRoomNo(),
                reservation.getReservationStatus().getName(),
                reservation.getStatus().name(),
                reservation.getCreatedAt(),
                reservation.getMessage());
    }
}

