
package com.sanhait.springfilesave.file.dto;

import lombok.Data;
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

    public Reservation clone() {
        return new Reservation(this.roomNo, this.reservationStatus, this.status, this.createdAt);
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

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

    public enum ReservationStatus {
        CHECKIN("입실"),
        CHECKOUT("퇴실");

        private String name;

        public String getName() {
            return this.name;
        }

        ReservationStatus(String name) {
            this.name = name;
        }
    }

    public enum Status {
        SUCCESS, FAIL
    }
}

