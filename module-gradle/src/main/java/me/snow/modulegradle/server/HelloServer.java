package me.snow.modulegradle.server;

import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class HelloServer {
    public void print() {
        System.out.println("123123123");
    }

    public void increase(String roomNo) {
        synchronized (HelloServer.class) {
            if (RoomManagement.isRoomOccupied(roomNo)) {
                throw new RuntimeException("이미 체크인 된 객실입니다.");
            }
        }
        // T1 T2 T3 T4 T5
        RoomManagement.setRoomOccupied(roomNo, true);

//        RoomManagement.setRoomOccupied(roomNo);

        try {
            // 체크인
//            System.out.println(Thread.currentThread().getName());

            RoomManagement.increase();
            Thread.sleep(3);
            RoomManagement.decrease();

//            RoomManagement.removeCache(roomNo);
        } catch (Exception e) {
//            RoomManagement.removeCache(roomNo);
            System.err.println(e.getMessage());


//            throw new RuntimeException(e);
//            throw new BusinessException(ErrorCode.DATA_STATUS_ERROR, "사용 불가능한 객실이거나 내부 서비스 오류입니다.");
        } finally {
            RoomManagement.removeCache(roomNo);
        }
    }
}
