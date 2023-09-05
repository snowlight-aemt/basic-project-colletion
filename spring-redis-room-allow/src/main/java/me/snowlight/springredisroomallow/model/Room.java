package me.snowlight.springredisroomallow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @GeneratedValue
    @Id
    private Long id;
    private String roomNo;

    private String roomName;
    private String roomType;
    private String roomStatus; // Vacant Occupied
    private String roomCleanStatus; // Dirty Clean

    public void allow() {
        this.roomStatus = "Occupied";
        this.roomCleanStatus = "Dirty";
    }

    public boolean isNotAllowed() {
        return !this.getRoomStatus().equals("Vacant") || !this.getRoomCleanStatus().equals("Clean");
    }
}
