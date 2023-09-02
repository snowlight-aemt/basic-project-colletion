package me.snowlight.springredisroomallow.model;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcRoomRepository implements RoomRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from room where id = ?", roomMapper, id));
//
    }

    @Override
    public List<Room> findAll() {
        return jdbcTemplate.query("select * from room", roomMapper);
    }

    @Override
    public void save(Room room) {
        jdbcTemplate.update("update room " +
                "set room_status = ?, " +
                "       room_clean_status = ? " +
                "where id = ?", room.getRoomStatus(), room.getRoomCleanStatus(), room.getId());
    }

    static RowMapper<Room> roomMapper = (rs, rowNum) -> new Room(
            rs.getLong("ID"),
            rs.getString("ROOM_NO"),
            rs.getString("ROOM_NAME"),
            rs.getString("ROOM_TYPE"),
            rs.getString("ROOM_STATUS"),
            rs.getString("ROOM_CLEAN_STATUS"));



//    private final MessageSource messageSource;

//    public JdbcUserRepository(JdbcTemplate jdbcTemplate, MessageSource messageSource) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.messageSource = messageSource;
//    }
//
//    @Override
//    public List<User> findAll() {
//        List<User> users = jdbcTemplate.query("SELECT SEQ, EMAIL, PASSWD , LOGIN_COUNT , LAST_LOGIN_AT , CREATE_AT  FROM USER ORDER BY SEQ", userMapper);
//        return !users.isEmpty() ? users : null;
//    }
}
