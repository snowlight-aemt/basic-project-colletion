package me.snowlight.codingtestspringredis.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessage implements Serializable {
    private static final long serialVersionUID = 2082503192322391880L;
    private String roomId;
    private String name;
    private String message;
}