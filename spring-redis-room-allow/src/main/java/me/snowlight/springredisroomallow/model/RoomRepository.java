package me.snowlight.springredisroomallow.model;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Optional<Room> findById(Long id);

    List<Room> findAll();

    void save(Room room);

//    @Query("select r from Room r where r.roomStatus = 'Vacant' and r.roomCleanStatus = 'Clean'")
//    List<Room> findRoomsEnableAllowed();

//    @Query("select r from Room r where r.id = :id")
//    @Query(value = "select * from room where id = :id", nativeQuery = true)
//    Optional<Room> findById(@Param("id") Long id);
}
