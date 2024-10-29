package com.sanhait.springfilesave.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();
    private String roomNo;
}
