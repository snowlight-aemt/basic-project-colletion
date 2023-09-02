package me.snowlight.springredisroomallow.model;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcRoomHistoryRepository implements RoomHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(RoomHistory roomHistory) {
        jdbcTemplate.update("insert into room_history (id, room_id) " +
                "values (?, ?)", roomHistory.getId(), roomHistory.getRoomId());
    }
}
