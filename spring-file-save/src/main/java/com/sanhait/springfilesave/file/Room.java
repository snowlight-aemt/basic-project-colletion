
package com.sanhait.springfilesave.file;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Room implements Serializable {
    private int seqNo;
    private String roomNo;
    private RoomCleanStatus roomCleanStatus;
    private RoomStatus roomStatus;
    private String status;
    private LocalDateTime createdAt;

    public Room clone() {
        return new Room(this.seqNo, this.roomNo, this.roomCleanStatus, this.roomStatus, this.status, this.createdAt);
    }

    public Room(String roomNo, RoomCleanStatus roomCleanStatus, RoomStatus roomStatus, String status, LocalDateTime createdAt) {
        this.roomNo = roomNo;
        this.roomCleanStatus = roomCleanStatus;
        this.roomStatus = roomStatus;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Room(int seqNo, String roomNo, RoomCleanStatus roomCleanStatus, RoomStatus roomStatus, String status, LocalDateTime createdAt) {
        this.seqNo = seqNo;
        this.roomNo = roomNo;
        this.roomCleanStatus = roomCleanStatus;
        this.roomStatus = roomStatus;
        this.status = status;
        this.createdAt = createdAt;
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

    public RoomCleanStatus getRoomCleanStatus() {
        return roomCleanStatus;
    }

    public void setRoomCleanStatus(RoomCleanStatus roomCleanStatus) {
        this.roomCleanStatus = roomCleanStatus;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum RoomStatus {
        E("재실"),
        T("외출");

        private String name;

        public String getName() {
            return this.name;
        }

        RoomStatus(String name) {
            this.name = name;
        }
    }

    public enum RoomCleanStatus {
        G("청소 지시"),
        C("청소 중"),
        K("점검 완료"),
        I("청소 완료"),
        W("청소 대기 (PMS 용)");

        private String name;

        public String getName() {
            return this.name;
        }

        RoomCleanStatus(String name) {
            this.name = name;
        }
    }
}

