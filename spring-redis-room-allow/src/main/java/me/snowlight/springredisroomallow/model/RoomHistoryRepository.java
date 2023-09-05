package me.snowlight.springredisroomallow.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomHistoryRepository extends JpaRepository<RoomHistory, Long> {
//    void save(RoomHistory roomHistory);
}
