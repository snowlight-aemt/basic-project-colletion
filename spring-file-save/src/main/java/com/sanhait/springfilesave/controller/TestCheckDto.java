package com.sanhait.springfilesave.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestCheckDto {
    private String room;
    private String start;
    private String end;
}