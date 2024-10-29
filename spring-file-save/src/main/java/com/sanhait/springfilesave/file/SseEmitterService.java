package com.sanhait.springfilesave.file;

import com.sanhait.springfilesave.file.dto.ReservationDto;
import com.sanhait.springfilesave.file.dto.RoomDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SseEmitterService {
    // thread-safe 한 컬렉션 객체로 sse emitter 객체를 관리해야 한다.
    private static final Map<String, SseEmitter> emitterReservationMap = new ConcurrentHashMap<>();
    private static final Map<String, SseEmitter> emitterRoomMap = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 1000 * 60 * 5;
    private static final long RECONNECTION_TIMEOUT = 1000 * 10   ;

    public SseEmitter reservationSubscribe(String id) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);

        emitter.onTimeout(() -> {
            log.info("RSVN server sent event timed out : id={}", id);
            emitter.complete();
        });

        emitter.onError(e -> {
            log.info("RSVN server sent event error occurred : id={}, message={}", id, e.getMessage());
            emitter.complete();
        });

        emitter.onCompletion(() -> {
            log.info("RSVN count : {}", emitterReservationMap.size());
            if (emitterReservationMap.remove(id) != null) {
                log.info("RSVN server sent event removed in emitter cache: id={}", id);
            }

            log.error("RSVN disconnected by completed server sent event: id={}", id);
        });

        emitterReservationMap.put(id, emitter);


        try {
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    //event 명 (event: event example)
//                    .name("event example")
                    //event id (id: id-1) - 재연결시 클라이언트에서 `Last-Event-ID` 헤더에 마지막 event id 를 설정
                    .id(String.valueOf(id))
                    //event data payload (data: SSE connected)
                    .data("SSE connected")
                    .reconnectTime(RECONNECTION_TIMEOUT);
            emitter.send(event);
        } catch (IOException e) {
            log.error("RSVN failure send media position data, id={}, {}", id, e.getMessage());
        }
        return emitter;
    }

    public SseEmitter roomSubscribe(String id) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);

        emitter.onTimeout(() -> {
            log.info("ROOM server sent event timed out : id={}", id);
            emitter.complete();
        });

        emitter.onError(e -> {
            log.error("ROOM server sent event error occurred : id={}, message={}", id, e.getMessage());
            emitter.complete();
        });

        emitter.onCompletion(() -> {
            log.info("ROOM count : {}", emitterRoomMap.size());
            if (emitterRoomMap.remove(id) != null) {
                log.info("ROOM server sent event removed in emitter cache: id={}", id);
            }

            log.error("ROOM disconnected by completed server sent event: id={}", id);
        });

        emitterRoomMap.put(id, emitter);

        try {
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .id(id)
                    .data("SSE connected")
                    .reconnectTime(RECONNECTION_TIMEOUT);
            emitter.send(event);
        } catch (IOException e) {
            log.error("ROOM failure send media position data, id={}, {}", id, e.getMessage());
        }
        return emitter;
    }

    public static void broadcastReservation(ReservationDto eventPayload) {
        for (String id : emitterReservationMap.keySet()) {
            try {
                SseEmitter emitter = emitterReservationMap.get(id);
                emitter.send(SseEmitter.event()
                        .name("reservation")
                        .id(id)
                        .reconnectTime(RECONNECTION_TIMEOUT)
                        .data(eventPayload, MediaType.APPLICATION_JSON)
                );
                log.info("RSVN sended notification, id={}, payload={}", id, eventPayload);
            } catch (IOException e) {
                emitterReservationMap.remove(id);
                log.error("broadcastReservation", e);
            }
        }
    }

    public static void broadcastRoom(RoomDto eventPayload) {
        for (String id : emitterRoomMap.keySet()) {
            SseEmitter emitter = emitterRoomMap.get(id);
            try {

                emitter.send(SseEmitter.event()
                        .name("room")
                        .id(id)
                        .reconnectTime(RECONNECTION_TIMEOUT)
                        .data(eventPayload, MediaType.APPLICATION_JSON)
                );
                log.info("ROOM sended notification, id={}, payload={}", id, eventPayload);

            } catch (IOException e) {
                emitterRoomMap.remove(id);
                log.error("broadcastRoom", e);
            }
        }
    }
}