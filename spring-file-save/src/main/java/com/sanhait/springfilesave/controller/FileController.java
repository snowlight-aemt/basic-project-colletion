package com.sanhait.springfilesave.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanhait.springfilesave.file.RoomHistoryRepository;
import com.sanhait.springfilesave.file.dto.ReservationDto;
import com.sanhait.springfilesave.file.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final ObjectMapper objectMapper;

    @GetMapping("/file")
    public String file(Model model) {
        List<RoomDto> roomDtos = RoomHistoryRepository.findRoomAll().stream()
                                                    .map(RoomDto::room).toList();
        List<ReservationDto> reservationDtos = RoomHistoryRepository.findReservationAll().stream()
                                                    .map(ReservationDto::reservation).toList();

        model.addAttribute("rooms", roomDtos);
        model.addAttribute("reservations", reservationDtos);

        int maxSeqNo = RoomHistoryRepository.getMaxSeqNo();
        model.addAttribute("count", maxSeqNo);

        return "index";
    }

    @GetMapping("/room")
    public String room(@RequestParam(required = false) String roomNo, Model model) {
        List<RoomDto> roomDtos = new ArrayList<>();
        List<ReservationDto> reservations = new ArrayList<>();

        if (!StringUtils.hasText(roomNo)) {
            return "redirect:file";
        }

        List<String> roomList = RoomHistoryRepository.getRoomNo();
        for (var roomNoByFile : roomList) {
            if (roomNoByFile.equals(roomNo)) {
                roomDtos = RoomHistoryRepository.findRoomByRoomNo(roomNoByFile).stream()
                        .map(RoomDto::room).toList();
                reservations = RoomHistoryRepository.findReservationByRoomNo(roomNoByFile).stream()
                        .map(ReservationDto::reservation).toList();

                break;
            }
        }

        model.addAttribute("roomName", roomList);

        model.addAttribute("rooms", roomDtos);
        model.addAttribute("reservations", reservations);

        int maxSeqNo = RoomHistoryRepository.getMaxSeqNo();
        model.addAttribute("count", maxSeqNo);

        return "room";
    }
}
