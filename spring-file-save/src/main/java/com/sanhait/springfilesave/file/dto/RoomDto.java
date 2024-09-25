
package com.sanhait.springfilesave.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto implements Serializable {
    private int seqNo;
    private String roomNo;
    private String roomCleanStatus;
    private String roomStatus;
    private String status;
    private LocalDateTime createdAt;

    public static RoomDto room(Room room) {
        return new RoomDto(
                room.getSeqNo(),
                room.getRoomNo(),
                room.getRoomCleanStatus().getName(),
                room.getRoomStatus().getName(),
                room.getStatus(),
                room.getCreatedAt()
        );
    }
}

