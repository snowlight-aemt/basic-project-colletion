package com.sanhait.springfilesave.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sanhait.springfilesave.file.dto.Reservation;
import com.sanhait.springfilesave.file.dto.ReservationDto;
import com.sanhait.springfilesave.file.dto.Room;
import com.sanhait.springfilesave.file.dto.RoomDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class CacheRoomHistoryRepository {
    public static final int MAX_SEQ_NO = 100;

    public static int seqNo = 0;
    private static int maxSeqNo = 0;

    public static final String ROOT_PATH = "C:\\log\\";
    public static final String CONFIG_FILE_NAME = "config.txt";
    private static final Logger logger = LoggerFactory.getLogger(CacheRoomHistoryRepository.class);

    public static final String RESERVATION_FILE_NAME_CACHE = "reservation_cache.txt";
    public static final String ROOM_FILE_NAME_CACHE = "room_cache.txt";

    private static List<Room> cacheRoom = new CopyOnWriteArrayList<>();
    private static List<Reservation> cacheReservation = new CopyOnWriteArrayList<>();

    public static int getMaxSeqNo() {
        return maxSeqNo;
    }

    public static List<Room> getCacheRoom() {
        return cacheRoom;
    }

    public static List<Reservation> getCacheReservation() {
        return cacheReservation;
    }

    static {
        cacheRoom.addAll(findRoomCacheOnly(LocalDate.now()));
        cacheReservation.addAll(findReservationCacheOnly(LocalDate.now()));

        try {
            seqNo = findConfig(LocalDate.now()).getMaxSeqNo();
            saveConfig(seqNo);
        } catch (FileNotFoundException e) {
            seqNo = 0;
            saveConfig(seqNo);
        }
    }

    public static void saveRoomCached(Room room) {
        room.setSeqNo(getNewSeqNo());
        cacheRoom.add(room);
        SseEmitterService.broadcastRoom(RoomDto.dto(room));
    }

    public static void saveReservationCached(Reservation reservation) {
        reservation.setSeqNo(getNewSeqNo());
        cacheReservation.add(reservation);
        SseEmitterService.broadcastReservation(ReservationDto.dto(reservation));
    }

    public static List<Room> findRoomAll(LocalDate date) {
        List<Room> roomAll = RoomHistoryRepository.findRoomAll(date);
        // TODO 순서 확인.
        roomAll.addAll(findRoomCacheOnly(date));
        return roomAll;
    }

    public static List<Room> findRoomByRoomNo(LocalDate date, String roomNo) {
        List<Room> roomByRoomNo = RoomHistoryRepository.findRoomByRoomNo(date, roomNo);
        // TODO 순서 확인.
        roomByRoomNo.addAll(findRoomCacheOnly(date).stream()
                                .filter(r -> r.getRoomNo().equals(roomNo)).collect(Collectors.toList()));
        return roomByRoomNo;
    }

    public static List<Reservation> findReservationAll(LocalDate date) {
        List<Reservation> reservationAll = RoomHistoryRepository.findReservationAll(date);
        // TODO 순서 확인.
        reservationAll.addAll(findReservationCacheOnly(LocalDate.now()));
        return reservationAll;
    }

    public static List<Reservation> findReservationByRoomNo(LocalDate date, String roomNo) {
        List<Reservation> reservationByRoomNo = RoomHistoryRepository.findReservationByRoomNo(date, roomNo);
        // TODO 순서 확인.
        reservationByRoomNo.addAll(findReservationCacheOnly(LocalDate.now()).stream()
                                .filter(r -> r.getRoomNo().equals(roomNo)).collect(Collectors.toList()));
        return reservationByRoomNo;
    }

    public static List<String> getRoomNo(LocalDate date) {
        return RoomHistoryRepository.getRoomNo(date);
    }

    private synchronized static List<Room> findRoomCacheOnly(LocalDate date) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        StringBuffer stringBuffer = new StringBuffer();

        try {
            List<String> strings = Files.readAllLines(Util.getRootPath(date, ROOM_FILE_NAME_CACHE));

            stringBuffer.append("[");
            if (!strings.isEmpty()) {
                strings.stream().forEach(stringBuffer::append);
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            stringBuffer.append("]");
        } catch (IOException e) {
            return Collections.emptyList();
        }

        try {
            List<Room> rooms = objectMapper.readValue(stringBuffer.toString(), new TypeReference<List<Room>>() {});

            rooms.addAll(CacheRoomHistoryRepository.getCacheRoom());
            return rooms;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized static List<Reservation> findReservationCacheOnly(LocalDate date) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        StringBuffer stringBuffer1 = new StringBuffer();

        try {
            List<String> strings = Files.readAllLines(Util.getRootPath(date, RESERVATION_FILE_NAME_CACHE));

            stringBuffer1.append("[");
            if (!strings.isEmpty()) {
                strings.stream().forEach(stringBuffer1::append);
                stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
            }
            stringBuffer1.append("]");
        } catch (IOException e) {
            return Collections.emptyList();
        }

        try {
            List<Reservation> reservations = objectMapper.readValue(stringBuffer1.toString(), new TypeReference<List<Reservation>>() {});
            reservations.addAll(CacheRoomHistoryRepository.getCacheReservation());
            return reservations;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static void backupCacheRoom() {
        try {
            //
            logger.info("데이터 캐시 파일 동기화");
            cacheRoom();

            //
            logger.info("데이터 파일 동기화");
            for (Room room : findRoomCacheOnly(LocalDate.now())) {
                RoomHistoryRepository.saveRoom(LocalDate.now(), room);
            }

            logger.info("데이터 파일 삭제");
            Files.delete(Util.getRootPath(LocalDate.now(), ROOM_FILE_NAME_CACHE));
        } catch (IOException e) {
            logger.error("데이터가 없습니다.");
        }
    }

    public synchronized static void backupCacheReservation() {
        try {
            //
            logger.info("데이터 캐시 파일 동기화");
            cacheReservation();

            //
            logger.info("데이터 파일 동기화");
            for (Reservation reservation : findReservationCacheOnly(LocalDate.now())) {
                RoomHistoryRepository.saveReservation(LocalDate.now(), reservation);
            }

            logger.info("데이터 파일 삭제");
            Files.delete(Util.getRootPath(LocalDate.now(), RESERVATION_FILE_NAME_CACHE));
        } catch (IOException e) {
            logger.error("데이터가 없습니다.");
        }
    }

    public synchronized static Config findConfig(LocalDate date) throws FileNotFoundException {
        Path path = Util.getRootPath(date, CONFIG_FILE_NAME);

        if (!Files.exists(path)) {
            throw new FileNotFoundException();
        }

        try (
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis);
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

    // TODO 원복
    public synchronized static Config saveConfig(int seqNo) {
        LocalDate date = LocalDate.now();

        Path path = Util.getRootPath(date, CONFIG_FILE_NAME);

        try {
            Files.createDirectories(Util.getRootPath(date));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        maxSeqNo = seqNo + MAX_SEQ_NO;

        try (
                FileOutputStream fis = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fis);
        ) {
            oos.writeObject(new Config(maxSeqNo));
            return new Config(maxSeqNo);
        } catch (IOException e) {
            return new Config(MAX_SEQ_NO);
        }
    }

    public synchronized static int getseqNo(LocalDate date) {
        Path path = Util.getRootPath(date, CONFIG_FILE_NAME);

        try (
            FileInputStream fis = new FileInputStream(path.toFile());
            ObjectInputStream oos = new ObjectInputStream(fis);
        ) {
            Object o = oos.readObject();

            if (o instanceof Config) {
                return ((Config) o).getMaxSeqNo();
            } else {
                return 0;
            }
        } catch (IOException | ClassNotFoundException e) {
            return 0;
        }
    }

    public synchronized static int getNewSeqNo() {
        seqNo++;
        if (seqNo < maxSeqNo) {
            return seqNo;
        }

        saveConfig(seqNo);
        return maxSeqNo;
    }

    public synchronized static void cacheRoom() {
                                ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            Files.createDirectories(Util.getRootPath());
        } catch (IOException e) {
            logger.error("기본 저장 폴더 생성 중 에러 발생");
            throw new RuntimeException(e);
        }

        Path path = Util.getRootPath(ROOM_FILE_NAME_CACHE);

        try (BufferedWriter bufferedWriter =
                     Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            for (Room room : getCacheRoom()) {
                room.setSeqNo(getNewSeqNo());

                bufferedWriter.write(objectMapper.writeValueAsString(room));
                bufferedWriter.write(",");
                bufferedWriter.newLine();

                System.out.println(room);
            }
        } catch (IOException e) {
            logger.error("기본 저장 폴더 생성 중 에러 발생 1");
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("기본 저장 폴더 생성 중 에러 발생 2");
            throw new RuntimeException(e);
        } finally {
            cacheRoom = new CopyOnWriteArrayList<>();
        }
    }

    public synchronized static void cacheReservation() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            Files.createDirectories(Util.getRootPath());
        } catch (IOException e) {
            logger.error("이력 저장 중 에러 발생");
            throw new RuntimeException(e);
        }

        Path path = Util.getRootPath(RESERVATION_FILE_NAME_CACHE);

        try (BufferedWriter bufferedWriter =
                     Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ) {
            for (Reservation reservation : getCacheReservation()) {
                reservation.setSeqNo(getNewSeqNo());

                bufferedWriter.write(objectMapper.writeValueAsString(reservation));
                bufferedWriter.write(",");
                bufferedWriter.newLine();

                System.out.println(reservation);
            }
        } catch (IOException e) {
            logger.error("이력 저장 중 에러 발생 1");
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("이력 저장 중 에러 발생 2");
            throw new RuntimeException(e);
        } finally {
            cacheReservation = new CopyOnWriteArrayList<>();
        }

    }

}
