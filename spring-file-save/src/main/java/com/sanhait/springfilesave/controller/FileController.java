package com.sanhait.springfilesave.controller;

import com.sanhait.springfilesave.file.CacheRoomHistoryRepository;
import com.sanhait.springfilesave.file.RoomHistoryRepository;
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

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/monitoring")
@RequiredArgsConstructor
public class FileController {
    private final SseEmitterService sseEmitterService;

    @GetMapping("/home")
    public String cache(HomeDto homeDto, Model model) {
        List<RoomDto> roomDtos = CacheRoomHistoryRepository.findRoomAll(homeDto.getDate()).stream()
                .map(RoomDto::dto).collect(Collectors.toList());

        List<ReservationDto> reservationDtos = CacheRoomHistoryRepository.findReservationAll(homeDto.getDate()).stream()
                .map(ReservationDto::dto).collect(Collectors.toList());

        List<String> roomList = CacheRoomHistoryRepository.getRoomNo(homeDto.getDate());

        model.addAttribute("roomName", roomList);

        model.addAttribute("date", homeDto.getDate());
        model.addAttribute("rooms", roomDtos);
        model.addAttribute("reservations", reservationDtos);

        int maxSeqNo = CacheRoomHistoryRepository.getMaxSeqNo();
        model.addAttribute("count", maxSeqNo);

        return "index";
    }

    @GetMapping("/home/log")
    public String log(HomeDto homeDto, Model model) {
        List<RoomDto> roomDtos = RoomHistoryRepository.findRoomAll(homeDto.getDate()).stream()
                .map(RoomDto::dto).collect(Collectors.toList());

        List<ReservationDto> reservationDtos = RoomHistoryRepository.findReservationAll(homeDto.getDate()).stream()
                .map(ReservationDto::dto).collect(Collectors.toList());


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
    public String room(RoomRequestDto roomRequestDto, Model model) {
        List<RoomDto> roomDtos = new ArrayList<>();
        List<ReservationDto> reservations = new ArrayList<>();

        if (!StringUtils.hasText(roomRequestDto.getRoomNo())) {
            return "redirect:file";
        }

        List<String> roomList = CacheRoomHistoryRepository.getRoomNo(roomRequestDto.getDate());
        for (String roomNoByFile : roomList) {
            if (roomNoByFile.equals(roomRequestDto.getRoomNo())) {
                log.info("room3");
                roomDtos = CacheRoomHistoryRepository.findRoomByRoomNo(roomRequestDto.getDate(), roomNoByFile).stream()
                        .map(RoomDto::dto).collect(Collectors.toList());
                log.info("room4");
                reservations = CacheRoomHistoryRepository.findReservationByRoomNo(roomRequestDto.getDate(), roomNoByFile).stream()
                        .map(ReservationDto::dto).collect(Collectors.toList());

                break;
            }
        }

        model.addAttribute("roomName", roomList);

        model.addAttribute("date", roomRequestDto.getDate());
        model.addAttribute("rooms", roomDtos);
        model.addAttribute("reservations", reservations);

        int maxSeqNo = CacheRoomHistoryRepository.getMaxSeqNo();
        model.addAttribute("count", maxSeqNo);

        return "room";
    }

    @GetMapping(path = "/sse/subscribe/reservation", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> reservationSubscribe() {
        String sseId = UUID.randomUUID().toString();
        SseEmitter emitter = sseEmitterService.reservationSubscribe(sseId);

        if (emitter == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(emitter);
    }

    @GetMapping(path = "/sse/subscribe/room", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> roomSubscribe() {
        String sseId = UUID.randomUUID().toString();
        SseEmitter emitter = sseEmitterService.roomSubscribe(sseId);

        if (emitter == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(emitter);
    }

    @ResponseBody
    @GetMapping("/run/pid")
    public ResponseEntity<String> getPID() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0]; // "pid@hostname" 형식에서 pid 부분만 추출

        System.out.println("Current PID: " + pid);
        return ResponseEntity.ok(pid);
    }

    @ResponseBody
    @GetMapping("/run/shutdown")
    public void run() {
        System.exit(0);
    }



    @GetMapping("/test/checkin")
    @ResponseBody
    public void checkin() {
        Random random = new Random(System.currentTimeMillis());

        try {
            List<Reservation> reservations = Arrays.asList(
                    new Reservation("0305", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now()),
                    new Reservation("0307", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now()),
                    new Reservation("0305", Reservation.ReservationStatus.CHECKOUT, Reservation.Status.SUCCESS, LocalDateTime.now()),
                    new Reservation("0306", Reservation.ReservationStatus.CHECKIN, Reservation.Status.SUCCESS, LocalDateTime.now())
            );
            for (int j = 0; j < 4; j++) {
                CacheRoomHistoryRepository.saveReservationCached(reservations.get(j));
            }
        } catch (Exception e) {
            log.error("TEST checkin");
        }
    }

    @GetMapping("/test/room")
    @ResponseBody
    public void checkout() {
        Random random = new Random(System.currentTimeMillis());

        try {
            List<Room> rooms = Arrays.asList(
                    new Room("0304", Room.RoomCleanStatus.C, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now()),
                    new Room("0305", Room.RoomCleanStatus.G, Room.RoomStatus.T, Room.Status.SUCCESS, LocalDateTime.now()),
                    new Room("0306", Room.RoomCleanStatus.K, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now()),
                    new Room("0307", Room.RoomCleanStatus.C, Room.RoomStatus.E, Room.Status.SUCCESS, LocalDateTime.now())
            );
            for (int j = 0; j < 4; j++) {
                CacheRoomHistoryRepository.saveRoomCached(rooms.get(j));
            }
        } catch (Exception e) {
            log.error("TEST room");
        }
    }
}
