package com.sanhait.springfilesave.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class TestCleanDto {
    private String roomState;
    private String roomCleanState;
    private String roomNo;
}