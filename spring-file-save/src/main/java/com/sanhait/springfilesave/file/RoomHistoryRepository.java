package com.sanhait.springfilesave.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sanhait.springfilesave.file.dto.Reservation;
import com.sanhait.springfilesave.file.dto.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class RoomHistoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(RoomHistoryRepository.class);
    public static final int MAX_SEQ_NO = 1000;

    public static int seqNo = 0;
    private static int maxSeqNo = 0;

    public static final String ROOT_PATH = "C:\\log\\";
    public static final String CONFIG_FILE_NAME = "config.txt";
    public static final String RESERVATION_FILE_NAME = "reservation.txt";
    public static final String ROOM_FILE_NAME = "room.txt";

    static {
        try {
            seqNo = findConfig().getMaxSeqNo();
            saveConfig(seqNo);
        } catch (FileNotFoundException e) {
            seqNo = 0;
            saveConfig(seqNo);
        }
    }

    public static int getMaxSeqNo() {
        return maxSeqNo;
    }

    public synchronized static List<Room> findRoomAll() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer stringBuffer = new StringBuffer();

        try {
            List<String> strings = Files.readAllLines(Paths.get("C:\\log\\" + now + "\\room.txt"));

            stringBuffer.append("[");
            strings.stream().forEach(stringBuffer::append);
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.append("]");
            objectMapper.registerModule(new JavaTimeModule());
        } catch (IOException e) {
            return Collections.emptyList();
        }

        try {
            List<Room> rooms = objectMapper.readValue(stringBuffer.toString(), new TypeReference<List<Room>>() {});
            return rooms;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized static List<Room> findRoomByRoomNo(String roomNo) {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer stringBuffer = new StringBuffer();

        try {
            List<String> strings = Files.readAllLines(Paths.get("C:\\log\\" + now + "\\" + roomNo + "\\room.txt"));

            stringBuffer.append("[");
            strings.stream().forEach(stringBuffer::append);
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.append("]");
        } catch (IOException e) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(stringBuffer.toString(), new TypeReference<List<Room>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static List<Reservation> findReservationByRoomNo(String roomNo) {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer stringBuffer1 = new StringBuffer();
        try {
            List<String> strings = Files.readAllLines(Paths.get("C:\\log\\" + now + "\\" + roomNo + "\\reservation.txt"));

            stringBuffer1.append("[");
            strings.stream().forEach(stringBuffer1::append);
            stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
            stringBuffer1.append("]");

        } catch (IOException e) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(stringBuffer1.toString(), new TypeReference<List<Reservation>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static List<Reservation> findReservationAll() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer stringBuffer1 = new StringBuffer();
        try {
            List<String> strings = Files.readAllLines(Paths.get("C:\\log\\" + now + "\\reservation.txt"));

            stringBuffer1.append("[");
            strings.stream().forEach(stringBuffer1::append);
            stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
            stringBuffer1.append("]");

            objectMapper.registerModule(new JavaTimeModule());
        } catch (IOException e) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(stringBuffer1.toString(), new TypeReference<List<Reservation>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static List<String> getRoomNo() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        try {
            return Files.list(Paths.get("C:\\log\\" + now))
                    .filter(Files::isDirectory)
                    .map(Path::toString).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static void saveRoom(Room room) {
        room.setSeqNo(getNewSeqNo());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            Files.createDirectories(getRootPath());
            Files.createDirectories(getRootPath(room.getRoomNo()));
        } catch (IOException e) {
            logger.error("기본 저장 폴더 생성 중 에러 발생");
            throw new RuntimeException(e);
        }

        Path path = getRootPath(ROOM_FILE_NAME);
        Path roomPath = getRootPath(room.getRoomNo(), ROOM_FILE_NAME);

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

    public synchronized static void saveReservation(Reservation reservation) {
        reservation.setSeqNo(getNewSeqNo());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            Files.createDirectories(getRootPath());
            Files.createDirectories(getRootPath(reservation.getRoomNo()));
        } catch (IOException e) {
            logger.error("이력 저장 중 에러 발생");
            throw new RuntimeException(e);
        }

        Path path = getRootPath(RESERVATION_FILE_NAME);
        Path roomPath = getRootPath(reservation.getRoomNo(), RESERVATION_FILE_NAME);

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

    public synchronized static Config findConfig() throws FileNotFoundException {
        Path path = getRootPath(CONFIG_FILE_NAME);

        if (!Files.exists(path)) {
            throw new FileNotFoundException();
        }

        try (
            var fis = new FileInputStream(path.toFile());
            var ois = new ObjectInputStream(fis);
        ) {
            Object object = ois.readObject();
            if (object instanceof Config) {
                return ((Config) object);
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }

    public synchronized static Config saveConfig(int seqNo) {
        Path path = getRootPath(CONFIG_FILE_NAME);

        try {
            Files.createDirectories(getRootPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        maxSeqNo = seqNo + MAX_SEQ_NO;

        try (
            var fis = new FileOutputStream(path.toFile());
            var oos = new ObjectOutputStream(fis);
        ) {
            oos.writeObject(new Config(maxSeqNo));
            return new Config(maxSeqNo);
        } catch (IOException e) {
            return new Config(MAX_SEQ_NO);
        }
    }

    private synchronized static int getNewSeqNo() {
        seqNo++;
        if (seqNo < maxSeqNo) {
            return seqNo;
        }

        saveConfig(seqNo);
        return maxSeqNo;
    }

    private static Path getRootPath() {
        return getRootPath(null);
    }

    private static Path getRootPath(String... path) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (path == null) {
            return Paths.get(ROOT_PATH, now);
        } else {
            return Paths.get(ROOT_PATH + now + "\\", path);
        }
    }

    public static void deleteAll() {
        try {
            for (var path : Files.list(getRootPath()).toList()) {
                logger.info(path.toString());
                Files.delete(path);
            }
            Files.deleteIfExists(getRootPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
