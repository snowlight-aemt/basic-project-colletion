
package com.sanhait.springfilesave.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RoomDto implements Serializable {
    private int seqNo;
    private String roomNo;
    private String roomCleanStatus;
    private String roomStatus;
    private String status;
    private LocalDateTime createdAt;
    private String message = "";

    public RoomDto(int seqNo, String roomNo, String roomCleanStatus, String roomStatus, String status, LocalDateTime createdAt, String message) {
        this.seqNo = seqNo;
        this.roomNo = roomNo;
        this.roomCleanStatus = roomCleanStatus;
        this.roomStatus = roomStatus;
        this.status = status;
        this.createdAt = createdAt;
        this.message = message;
    }

    public static RoomDto dto(Room room) {
        return new RoomDto(
                room.getSeqNo(),
                room.getRoomNo(),
                room.getRoomCleanStatus().getName(),
                room.getRoomStatus().getName(),
                room.getStatus().name(),
                room.getCreatedAt(),
                room.getMessage()
        );
    }
}

