package me.snowlight.springredisroomallow;

import lombok.Getter;

public class CountClass {
    @Getter
    private static int count = 0;
    public synchronized static void increase() {
        count += 1;
    }
}
