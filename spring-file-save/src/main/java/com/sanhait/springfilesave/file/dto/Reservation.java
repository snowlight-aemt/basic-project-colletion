
package com.sanhait.springfilesave.file.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Reservation implements Serializable {
    private int seqNo;
    private String roomNo;
    private ReservationStatus reservationStatus;
    private Status status;
    private LocalDateTime createdAt;
    private String message = "";

    public Reservation(String roomNo, ReservationStatus reservationStatus, Status status, LocalDateTime createdAt, String message) {
        this.roomNo = roomNo;
        this.reservationStatus = reservationStatus;
        this.status = status;
        this.createdAt = createdAt;
        this.message = message;
    }

    public Reservation(String roomNo, ReservationStatus reservationStatus, Status status, LocalDateTime createdAt) {
        this.roomNo = roomNo;
        this.reservationStatus = reservationStatus;
        this.status = status;
        this.createdAt = createdAt;
    }

    @Getter
    public enum ReservationStatus {
        CHECKIN("입실"),
        CHECKOUT("퇴실");

        private String name;

        ReservationStatus(String name) {
            this.name = name;
        }
    }

    public enum Status {
        SUCCESS, FAIL
    }
}

