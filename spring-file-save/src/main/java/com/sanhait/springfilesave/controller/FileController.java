package com.sanhait.springfilesave.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanhait.springfilesave.file.CacheRoomHistoryRepository;
import com.sanhait.springfilesave.file.RoomHistoryRepository;
import com.sanhait.springfilesave.file.SchedulerCachedSync;
import com.sanhait.springfilesave.file.SseEmitterService;
import com.sanhait.springfilesave.file.dto.Reservation;
import com.sanhait.springfilesave.file.dto.ReservationDto;
import com.sanhait.springfilesave.file.dto.Room;
import com.sanhait.springfilesave.file.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Controller
@RequestMapping("/monitoring")
@RequiredArgsConstructor
public class FileController {
    private final ObjectMapper objectMapper;

    @GetMapping("/home")
    public String cache(HomeDto homeDto, Model model) {
        List<RoomDto> roomDtos = CacheRoomHistoryRepository.findRoomAll(homeDto.getDate()).stream()
                                                    .map(RoomDto::dto).toList();

        List<ReservationDto> reservationDtos = CacheRoomHistoryRepository.findReservationAll(homeDto.getDate()).stream()
                                                    .map(ReservationDto::dto).toList();

        List<String> roomList = CacheRoomHistoryRepository.getRoomNo(homeDto.getDate());

        model.addAttribute("roomName", roomList);

        model.addAttribute("date", homeDto.getDate());
        model.addAttribute("rooms", roomDtos);
        model.addAttribute("reservations", reservationDtos);

        int maxSeqNo = CacheRoomHistoryRepository.getMaxSeqNo();
        model.addAttribute("count", maxSeqNo);

        return "index";
    }

    @ResponseBody
    @GetMapping("/run/service")
    public ResponseEntity<String> run() {
        String path = "C:\\log\\run\\restart_room_service_sanhait.bat.lnk";

        try {
            if (new File(path).isFile()) {
                Runtime.getRuntime().exec("cmd /c " + path);

                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.status(500).body("실행 파일의 위치를 확인해주세요. (C:\\log\\run\\restart_room_service_sanhait.bat.lnk)");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/home/log")
    public String log(HomeDto homeDto, Model model) {

        List<RoomDto> roomDtos = RoomHistoryRepository.findRoomAll(homeDto.getDate()).stream()
                .map(RoomDto::dto).toList();

        List<ReservationDto> reservationDtos = RoomHistoryRepository.findReservationAll(homeDto.getDate()).stream()
                .map(ReservationDto::dto).toList();


        List<String> roomList = RoomHistoryRepository.getRoomNo(homeDto.getDate());

        model.addAttribute("roomName", roomList);

        model.addAttribute("date", homeDto.getDate());
        model.addAttribute("rooms", roomDtos);
        model.addAttribute("reservations", reservationDtos);

        int maxSeqNo = CacheRoomHistoryRepository.getseqNo(homeDto.getDate());
        model.addAttribute("count", maxSeqNo);

        return "log";
    }

    @GetMapping("/room")
    public String room(com.sanhait.springfilesave.controller.RoomDto roomDto, Model model) {
        log.info("room");
        List<RoomDto> roomDtos = new ArrayList<>();
        List<ReservationDto> reservations = new ArrayList<>();

        if (!StringUtils.hasText(roomDto.getRoomNo())) {
            return "redirect:file";
        }

        List<String> roomList = CacheRoomHistoryRepository.getRoomNo(roomDto.getDate());
        for (var roomNoByFile : roomList) {
            if (roomNoByFile.equals(roomDto.getRoomNo())) {
                roomDtos = CacheRoomHistoryRepository.findRoomByRoomNo(roomDto.getDate(), roomNoByFile).stream()
                        .map(RoomDto::dto).toList();
                reservations = CacheRoomHistoryRepository.findReservationByRoomNo(roomDto.getDate(), roomNoByFile).stream()
                        .map(ReservationDto::dto).toList();

                break;
            }
        }

        model.addAttribute("roomName", roomList);

        model.addAttribute("date", roomDto.getDate());
        model.addAttribute("rooms", roomDtos);
        model.addAttribute("reservations", reservations);

        int maxSeqNo = CacheRoomHistoryRepository.getMaxSeqNo();
        model.addAttribute("count", maxSeqNo);

        return "room";
    }

//    @ResponseBody
//    @GetMapping("/list")
//    public List<ReservationDto> reservation(@RequestParam(required = true) LocalDate date) {
//        return CacheRoomHistoryRepository.findReservationCacheOnly(date).stream().map(ReservationDto::dto).toList();
//    }
//
//    @ResponseBody
//    @GetMapping("/room/list")
//    public List<RoomDto> room(@RequestParam(required = true) LocalDate date) {
//        return CacheRoomHistoryRepository.findRoomCacheOnly(date).stream().map(RoomDto::dto).toList();
//    }








    private final SseEmitterService sseEmitterService;

    //응답 mime type 은 반드시 text/event-stream 이여야 한다.
    //클라이언트로 부터 SSE subscription 을 수락한다.
    @GetMapping(path = "/sse/subscribe/reservation", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> reservationSubscribe() {
        String sseId = UUID.randomUUID().toString();
        SseEmitter emitter = sseEmitterService.reservationSubscribe(sseId);
        return ResponseEntity.ok(emitter);
    }

    @GetMapping(path = "/sse/subscribe/room", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> roomSubscribe() {
        String sseId = UUID.randomUUID().toString();
        SseEmitter emitter = sseEmitterService.roomSubscribe(sseId);
        return ResponseEntity.ok(emitter);
    }
    //    @PostMapping(path = "/v1/sse/broadcast")
    //    public ResponseEntity<Void> broadcast(@RequestBody EventPayload eventPayload) {
    //        sseEmitterService.broadcast(eventPayload);
    //        return ResponseEntity.ok().build();
    //    }
    @GetMapping("/test/checkin")
    @ResponseBody
    public void checkin() {
        Random random = new Random(System.currentTimeMillis());

        List<Reservation> reservations = Arrays.asList(
                new Reservation("0305", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now())
//                new Reservation("0307", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now()),
//                new Reservation("0305", Reservation.ReservationStatus.CHECKOUT, Reservation.Status.SUCCESS, LocalDateTime.now()),
//                new Reservation("0306", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now())
        );
        for (int j = 0; j < 1; j++) {
//            int i = random.nextInt(4);
            CacheRoomHistoryRepository.saveReservationCached(reservations.get(j).clone());
        }

    }

    @GetMapping("/test/room")
    @ResponseBody
    public void checkout() {
        Random random = new Random(System.currentTimeMillis());

        List<Room> rooms = Arrays.asList(
//                new Room("0304", Room.RoomCleanStatus.C, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now()),
                new Room("0305", Room.RoomCleanStatus.G, Room.RoomStatus.T, Room.Status.SUCCESS, LocalDateTime.now())
//                new Room("0306", Room.RoomCleanStatus.K, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now()),
//                new Room("0307", Room.RoomCleanStatus.C, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now())
        );
        for (int j = 0; j < 1; j++) {
//            int i = random.nextInt(4);
            CacheRoomHistoryRepository.saveRoomCached(rooms.get(j).clone());
        }
    }








    @GetMapping("sync")
    public void sync() {
        new SchedulerCachedSync().sync();
    }

    @GetMapping("test")
    public void test() {
        List<Room> rooms = Arrays.asList(
                new Room("0304", Room.RoomCleanStatus.C, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now()),
                new Room("0305", Room.RoomCleanStatus.G, Room.RoomStatus.T, Room.Status.SUCCESS, LocalDateTime.now()),
                new Room("0306", Room.RoomCleanStatus.K, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now()),
                new Room("0307", Room.RoomCleanStatus.C, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now())
        );

        List<Reservation> reservations = Arrays.asList(
                new Reservation("0305", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now()),
                new Reservation("0307", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now()),
                new Reservation("0305", Reservation.ReservationStatus.CHECKOUT, Reservation.Status.SUCCESS, LocalDateTime.now()),
                new Reservation("0306", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now())
        );

        ExecutorService executorService = Executors.newFixedThreadPool(200);

        int count = 200 * 1;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        Random random = new Random(System.currentTimeMillis());
//        int iii = 200 / 10;
        int cnt = 0;
        while (true) {

            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    int i = random.nextInt(4);
                    CacheRoomHistoryRepository.saveRoomCached(rooms.get(i).clone());
//                    fileQueue.enQueueUpdateRoom(rooms.get(i).clone());
                    countDownLatch.countDown();
                }
            });
            cnt += 100;

            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    int i = random.nextInt(4);
                    CacheRoomHistoryRepository.saveReservationCached(reservations.get(i).clone());
//                    fileQueue.enQueueCheckInAndOut(reservations.get(i).clone());
                    countDownLatch.countDown();
                }
            });
            cnt += 100;

            if (cnt >= count) {
                break;
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
