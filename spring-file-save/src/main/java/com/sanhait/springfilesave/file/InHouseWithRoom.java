package com.sanhait.springfilesave.file;

import java.io.Serializable;
import java.util.List;

public class InHouseWithRoom implements Serializable {
    private String id;
    private String roomNo;
    private Reservation reservation;
//    private Room room;
    private List<Room> rooms;

//    public void init() {
//        this.id = UUID.randomUUID().toString();
//    }.rooms = rooms;
//    }
}