package com.sanhait.springfilesave.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileQueue {
//    private static final Logger logger = LoggerFactory.getLogger(FileQueue.class);
    private final RoomHistoryRepository roomHistoryRepository = new RoomHistoryRepository();

    private static int segNo = 0;
    public static int mixSegNo = 0;

//    public synchronized int getNewSeqNo() {
//        this.segNo++;
//        if (segNo < mixSegNo) {
//            return segNo;
//        }
//
//        Config config = roomHistoryRepository.saveConfig(segNo);
//        mixSegNo = config.maxSeqNo;
//        return config.getSeqNo();
//    }

    public FileQueue() {

//        init();

//        segNo = loadSegNo();

//        segNo = roomHistoryRepository.findConfig().getSeqNo();
//        logger.info("FileQueue" + segNo);

//        increment();
    }

    // TODO 동시성 이슈
//    public void enQueueUpdateRoom(Room room) {
//        int segNo = getNewSeqNo();
//        room.setSeqNo(segNo);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        try {
//            Files.createDirectories(getRootPath());
//            Files.createDirectories(getRootPath(room.getRoomNo()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Path path = getRootPath("room.txt");
//        Path roomPath = getRootPath(room.getRoomNo(), "room.txt");
//
//        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path,
//                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//             BufferedWriter roomBufferedWriter = Files.newBufferedWriter(roomPath,
//                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//        ) {
//            bufferedWriter.write(objectMapper.writeValueAsString(room));
//            bufferedWriter.write(",");
//            bufferedWriter.newLine();
//            roomBufferedWriter.write(objectMapper.writeValueAsString(room));
//            roomBufferedWriter.write(",");
//            roomBufferedWriter.newLine();
//        } catch (IOException e) {
//            logger.error("error");
//            throw new RuntimeException(e);
//        }
//    }
//
//    // TODO 동시성 이슈
//    public void enQueueCheckInAndOut(Reservation reservation) {
//        int segNo = getNewSeqNo();
//        reservation.setSeqNo(segNo);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        try {
//            Files.createDirectories(getRootPath());
//            Files.createDirectories(getRootPath(reservation.getRoomNo()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Path path = getRootPath("reservation.txt");
//        Path roomPath = getRootPath(reservation.getRoomNo(), "reservation.txt");
//
//        try (
//                BufferedWriter bufferedWriter = Files.newBufferedWriter(path,
//                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//                BufferedWriter roomBufferedWriter = Files.newBufferedWriter(roomPath,
//                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//        ) {
//            bufferedWriter.write(objectMapper.writeValueAsString(reservation));
//            bufferedWriter.write(",");
//            bufferedWriter.newLine();
//            roomBufferedWriter.write(objectMapper.writeValueAsString(reservation));
//            roomBufferedWriter.write(",");
//            roomBufferedWriter.newLine();
//        } catch (IOException e) {
//            logger.error("error");
//            throw new RuntimeException(e);
//        }
//    }


//    private Path getRootPath() {
//        return getRootPath(null);
//    }
//
//    private Path getRootPath(String... path) {
//        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//
//        if (path == null) {
//            return Paths.get("C:\\log\\", now);
//        } else {
//            return Paths.get("C:\\log\\" + now + "\\", path);
//        }
//    }

//    public static int getSegNo() {
//        return segNo;
//    }

//    public void init() {
//        try {
//
//            for (var path : Files.list(getRootPath()).toList()) {
//                logger.info(path.toString());
//                Files.delete(path);
//            }
//            Files.deleteIfExists(getRootPath());
//
//        } catch (DirectoryNotEmptyException e) {
//            logger.info("don't exists directory");
//        } catch (FileNotFoundException e) {
//            logger.info("don't exists file");
//        } catch (NoSuchFileException e) {
//            logger.info("don't exists file");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
