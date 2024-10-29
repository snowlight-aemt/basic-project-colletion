
package com.sanhait.springfilesave.file.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Room implements Serializable {
    private int seqNo;
    private String roomNo;
    private RoomCleanStatus roomCleanStatus;
    private RoomStatus roomStatus;
    private Status status;
    private LocalDateTime createdAt;
    private String message = "";

    public Room(String roomNo, RoomCleanStatus roomCleanStatus, RoomStatus roomStatus, Status status, LocalDateTime createdAt) {
        this.roomNo = roomNo;
        this.roomCleanStatus = roomCleanStatus;
        this.roomStatus = roomStatus;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Room(String roomNo, RoomCleanStatus roomCleanStatus, RoomStatus roomStatus, Status status, LocalDateTime createdAt, String message) {
        this.roomNo = roomNo;
        this.roomCleanStatus = roomCleanStatus;
        this.roomStatus = roomStatus;
        this.status = status;
        this.createdAt = createdAt;
        this.message = message;
    }

    public Room(int seqNo, String roomNo, RoomCleanStatus roomCleanStatus, RoomStatus roomStatus, Status status, LocalDateTime createdAt) {
        this.seqNo = seqNo;
        this.roomNo = roomNo;
        this.roomCleanStatus = roomCleanStatus;
        this.roomStatus = roomStatus;
        this.status = status;
        this.createdAt = createdAt;
    }

    @Getter
    public enum RoomStatus {
        E("재실"),
        T("외출"),
        VACANCY("공실");

        private String name;

        RoomStatus(String name) {
            this.name = name;
        }

        public static RoomStatus getEnum(String value) {
            for (RoomStatus roomStatus : RoomStatus.values()) {
                if (!StringUtils.hasText(value)) {
                    return VACANCY;
                } else {
                    return roomStatus;
                }
            }
            throw new IllegalArgumentException("Invalid RandomEnum value: " + value);
        }
    }

    @Getter
    public enum RoomCleanStatus {
        G("청소 지시"),
        C("청소 중"),
        K("점검 완료"),
        I("청소 완료"),
        W("청소 대기 (PMS 용)");

        private String name;

        RoomCleanStatus(String name) {
            this.name = name;
        }
    }

    public enum Status {
        SUCCESS, FAIL
    }
}

