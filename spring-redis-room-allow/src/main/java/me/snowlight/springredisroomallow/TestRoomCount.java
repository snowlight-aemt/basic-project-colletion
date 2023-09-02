package me.snowlight.springredisroomallow;

import lombok.Getter;

public class TestRoomCount {
    @Getter
    private static int count = 0;
    public synchronized static void increase() {
        count += 1;
    }
}
