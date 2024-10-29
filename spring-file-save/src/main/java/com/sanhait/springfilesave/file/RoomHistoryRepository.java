package com.sanhait.springfilesave.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sanhait.springfilesave.file.dto.Reservation;
import com.sanhait.springfilesave.file.dto.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomHistoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(RoomHistoryRepository.class);

    public static final String RESERVATION_FILE_NAME = "reservation.txt";
    public static final String ROOM_FILE_NAME = "room.txt";

    public synchronized static List<Room> findRoomAll(LocalDate date) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer stringBuffer = new StringBuffer();

        try {
            List<String> strings = Files.readAllLines(Util.getRootPath(date, ROOM_FILE_NAME));

            stringBuffer.append("[");
            strings.stream().forEach(stringBuffer::append);
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.append("]");
            objectMapper.registerModule(new JavaTimeModule());
        } catch (IOException e) {
            return new ArrayList<>();
        }

        try {
            List<Room> rooms = objectMapper.readValue(stringBuffer.toString(), new TypeReference<List<Room>>() {});
            return rooms;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static List<Reservation> findReservationAll(LocalDate date) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer stringBuffer1 = new StringBuffer();
        try {
            List<String> strings = Files.readAllLines(Util.getRootPath(date, RESERVATION_FILE_NAME));

            stringBuffer1.append("[");
            strings.stream().forEach(stringBuffer1::append);
            stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
            stringBuffer1.append("]");

            objectMapper.registerModule(new JavaTimeModule());
        } catch (IOException e) {
            return new ArrayList<>();
        }

        try {
            List<Reservation> reservations = objectMapper.readValue(stringBuffer1.toString(), new TypeReference<List<Reservation>>() {});
            return reservations;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static List<Room> findRoomByRoomNo(LocalDate date, String roomNo) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer stringBuffer = new StringBuffer();

        try {
            List<String> strings = Files.readAllLines(Util.getRootPath(date, roomNo, ROOM_FILE_NAME));
            stringBuffer.append("[");
            strings.stream().forEach(stringBuffer::append);
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.append("]");
        } catch (IOException e) {
            return new ArrayList<>();
        }

        try {
            List<Room> rooms = objectMapper.readValue(stringBuffer.toString(), new TypeReference<List<Room>>() {});
            return rooms;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static List<Reservation> findReservationByRoomNo(LocalDate date, String roomNo) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer stringBuffer1 = new StringBuffer();
        try {
            List<String> strings = Files.readAllLines(Util.getRootPath(date, roomNo, RESERVATION_FILE_NAME));

            stringBuffer1.append("[");
            strings.stream().forEach(stringBuffer1::append);
            stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
            stringBuffer1.append("]");

        } catch (IOException e) {
            return new ArrayList<>();
        }

        try {
            List<Reservation> reservations = objectMapper.readValue(stringBuffer1.toString(), new TypeReference<List<Reservation>>() {});
            return reservations;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static List<String> getRoomNo(LocalDate now) {
//        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        try {
            return Files.list(Util.getRootPath(now))
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public synchronized static void saveRoom(LocalDate date, Room room) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            Files.createDirectories(Util.getRootPath(date));
            Files.createDirectories(Util.getRootPath(date, room.getRoomNo()));
        } catch (IOException e) {
            logger.error("기본 저장 폴더 생성 중 에러 발생");
            throw new RuntimeException(e);
        }

        Path path = Util.getRootPath(date, ROOM_FILE_NAME);
        Path roomPath = Util.getRootPath(date, room.getRoomNo(), ROOM_FILE_NAME);

        try (
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            BufferedWriter roomBufferedWriter = Files.newBufferedWriter(roomPath,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ) {
            bufferedWriter.write(objectMapper.writeValueAsString(room));
            bufferedWriter.write(",");
            bufferedWriter.newLine();
            roomBufferedWriter.write(objectMapper.writeValueAsString(room));
            roomBufferedWriter.write(",");
            roomBufferedWriter.newLine();
        } catch (IOException e) {
            logger.error("기본 저장 폴더 생성 중 에러 발생");
            throw new RuntimeException(e);
        }
    }

    public synchronized static void saveReservation(LocalDate date, Reservation reservation) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            Files.createDirectories(Util.getRootPath(date));
            Files.createDirectories(Util.getRootPath(date, reservation.getRoomNo()));
        } catch (IOException e) {
            logger.error("이력 저장 중 에러 발생");
            throw new RuntimeException(e);
        }

        Path path = Util.getRootPath(date, RESERVATION_FILE_NAME);
        Path roomPath = Util.getRootPath(date, reservation.getRoomNo(), RESERVATION_FILE_NAME);

        try (
                BufferedWriter bufferedWriter = Files.newBufferedWriter(path,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                BufferedWriter roomBufferedWriter = Files.newBufferedWriter(roomPath,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ) {
            bufferedWriter.write(objectMapper.writeValueAsString(reservation));
            bufferedWriter.write(",");
            bufferedWriter.newLine();
            roomBufferedWriter.write(objectMapper.writeValueAsString(reservation));
            roomBufferedWriter.write(",");
            roomBufferedWriter.newLine();
        } catch (IOException e) {
            logger.error("이력 저장 중 에러 발생");
            throw new RuntimeException(e);
        }
    }
}
