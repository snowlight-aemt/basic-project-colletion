package me.snow.modulegradle.server;

import me.snow.modulegradle.error.RoomDuplicateException;

import java.util.concurrent.ConcurrentHashMap;

public class RoomManagement {
    private static final ConcurrentHashMap<String, Boolean> roomCache = new ConcurrentHashMap<>();

    public static boolean isRoomOccupied(String roomNo) {
        return roomCache.getOrDefault(roomNo, false);
    }

    public static synchronized void setRoomOccupied(String roomNo, boolean occupied) {
        roomCache.put(roomNo, occupied);
    }

    public static synchronized void removeCache(String roomNo) {
        roomCache.remove(roomNo);
    }




//    private static final ConcurrentHashMap<String, Long> roomCache = new ConcurrentHashMap<>();
//    public static final int ROOM_EXPIRE_TIME = 60 * 1000 * 5;
//    private static boolean isRoomOccupied(String roomNo) {
//        return roomCache.containsKey(roomNo);
//    }
//
//    public static synchronized void setRoomOccupied(String roomNo) {
//        if (isRoomOccupied(roomNo)) {
//            throw new RoomDuplicateException("이미 체크인 된 객실입니다.");
//        }
//        long expireTime = System.currentTimeMillis() + ROOM_EXPIRE_TIME;
//        roomCache.put(roomNo, expireTime);
//    }
//
//    public static synchronized void removeCache(String roomNo) {
//        roomCache.remove(roomNo);
//
//        removeRoomExpired();
//    }
//
//    public static void removeRoomExpired() {
//        roomCache.forEach((key, value) -> {
//            if (value <= System.currentTimeMillis()) {
//                roomCache.remove(key);
//            }
//        });
//    }






    // 테스트용
    private static int onlyOne = 0;
    public static synchronized void increase() {
        onlyOne += 1;
        if (onlyOne > 1) {
            onlyOne = 0;
            throw new RuntimeException("더블 체크인");
        }
    }
    public static synchronized void decrease() {
        onlyOne -= 1;

        if (onlyOne < 0) {
            onlyOne = 0;
        }
    }
}
