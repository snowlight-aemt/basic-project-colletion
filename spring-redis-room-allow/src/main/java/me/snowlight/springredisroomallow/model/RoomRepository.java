package me.snowlight.springredisroomallow.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

//    @Query("select r from Room r where r.roomStatus = 'Vacant' and r.roomCleanStatus = 'Clean'")
//    List<Room> findRoomsEnableAllowed();
}
