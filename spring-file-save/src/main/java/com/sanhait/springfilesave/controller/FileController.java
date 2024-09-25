package com.sanhait.springfilesave.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sanhait.springfilesave.file.dto.Reservation;
import com.sanhait.springfilesave.file.dto.ReservationDto;
import com.sanhait.springfilesave.file.dto.Room;
import com.sanhait.springfilesave.file.dto.RoomDto;
import com.sanhait.springfilesave.file.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final ObjectMapper objectMapper;

    @GetMapping("/file")
    public String file(Model model) {
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
            throw new RuntimeException(e);
        }

        StringBuffer stringBuffer1 = new StringBuffer();
        try {
            List<String> strings = Files.readAllLines(Paths.get("C:\\log\\" + now + "\\reservation.txt"));

            stringBuffer1.append("[");
            strings.stream().forEach(stringBuffer1::append);
            stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
            stringBuffer1.append("]");

            objectMapper.registerModule(new JavaTimeModule());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            List<Room> rooms = objectMapper.readValue(stringBuffer.toString(), new TypeReference<List<Room>>() {});
            List<RoomDto> roomDtos = rooms.stream().map(RoomDto::room).toList();

            model.addAttribute("rooms", roomDtos);

            List<Reservation> reservation = objectMapper.readValue(stringBuffer1.toString(), new TypeReference<List<Reservation>>() {});
            List<ReservationDto> reservationDtos = reservation.stream().map(ReservationDto::reservation).toList();

            int maxSeqNo = RoomHistoryRepository.getMaxSeqNo();

            model.addAttribute("reservations", reservationDtos);
            model.addAttribute("count", maxSeqNo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "index";
    }

    @GetMapping("/room")
    public String room(@RequestParam(required = false) String roomNo, Model model) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();

        List<RoomDto> roomDtos = new ArrayList<>();
        List<ReservationDto> reservation = new ArrayList<>();
        List<String> roomList = new ArrayList<>();

        if (!StringUtils.hasText(roomNo)) {
            return "redirect:file";
        }

        stringBuffer.append("[");
        stringBuffer1.append("[");
        try {
            for (var s : Files.list(Paths.get("C:\\log\\" + now)).toList()) {
                if (Files.isDirectory(s)) {
                    roomList.add(s.getFileName().toString());

                    if (s.getFileName().toString().equals(roomNo)) {
                        try {
                            List<String> strings = Files.readAllLines(Paths.get("C:\\log\\" + now + "\\" + s.getFileName() + "\\room.txt"));
                            strings.stream().forEach(stringBuffer::append);
                            objectMapper.registerModule(new JavaTimeModule());

                        } catch (IOException e) {
                            stringBuffer.append(" ");
                        }

                        try {
                            List<String> strings = Files.readAllLines(Paths.get("C:\\log\\" + now + "\\" + s.getFileName() + "\\reservation.txt"));
                            strings.stream().forEach(stringBuffer1::append);
                            objectMapper.registerModule(new JavaTimeModule());

                        } catch (IOException e) {
                            stringBuffer1.append(" ");
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append("]");
        stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
        stringBuffer1.append("]");
        try {
            List<Room> rooms = objectMapper.readValue(stringBuffer.toString(), new TypeReference<List<Room>>() {});
            roomDtos.addAll(rooms.stream().map(RoomDto::room).toList());
            List<Reservation> reservationi = objectMapper.readValue(stringBuffer1.toString(), new TypeReference<List<Reservation>>() {});
            List<ReservationDto> reservationDtos = reservationi.stream().map(ReservationDto::reservation).toList();
            reservation.addAll(reservationDtos);

            model.addAttribute("roomName", roomList);
            model.addAttribute("rooms", roomDtos);
            model.addAttribute("reservations", reservation);
            int maxSeqNo = RoomHistoryRepository.getMaxSeqNo();
            model.addAttribute("count", maxSeqNo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "room";
    }
}
